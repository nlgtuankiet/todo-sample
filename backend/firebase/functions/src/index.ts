import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';
import * as algoliasearch from 'algoliasearch'; // When using TypeScrip
import * as express from 'express';

const client = algoliasearch("HDFFEQFN5A", "30eb8267e15bea477a3f11edc5927984");
const app = express();
admin.initializeApp();
const firestore = admin.firestore();
const numberOfShard: number = 10
const taskIndexPostFix = "_task_index"

export const helloWorld = functions.https.onRequest((request, response) => {
    response.send("Hello from Firebase!");
});

/**
 * document startAt with TimeStamp bug
 * throw new IllegalArgumentException("
 * Invalid query. You are trying to start or end a query using a document for 
 * which the field '" + orderBy.getField() + "' is an uncommitted server 
 * timestamp. (Since the value of this field is unknown, you cannot start/end 
 * a query with it.)");
 */
export const updateTimeFields = functions.firestore.document('user/{userId}/task/{taskId}').onWrite(async (change, context) => {
    const createTime = change.after.createTime.seconds
    const updateTime = change.after.updateTime.seconds
    const docCreateTime = change.after.data["createTime"] as number
    const docUpdateTime = change.after.data["updateTime"] as number
    if (createTime !== docCreateTime || updateTime !== docUpdateTime) {
        await change.after.ref.set({
            'createTime': createTime,
            'updateTime': updateTime
        }, { merge: true })
    }
})

// TODO optimize
export const modifyShardCount = functions.firestore.document('user/{userId}/task/{taskId}').onWrite(async (change, context) => {
    const userId = context.params["userId"] as string
    // TODO validate stuff
    // init shard

    const userRef = firestore.collection("user").doc(userId)
    const totalShardRef = userRef.collection("totalShard")
    const activeShardRef = userRef.collection("activeShard")
    const completedShardRef = userRef.collection("completedShard")
    await firestore.runTransaction(async (transaction) => {
        await initShard(transaction, totalShardRef)
    })
    await firestore.runTransaction(async (transaction) => {
        await initShard(transaction, activeShardRef)
    })
    await firestore.runTransaction(async (transaction) => {
        await initShard(transaction, completedShardRef)
    })

    let totalChangeAmount = 0;
    let newTotal: number = null;
    let newTotalIndex: number = null;
    let activeChangeAmount = 0;
    let newActive: number = null;
    let newActiveIndex: number = null;
    let completedChangeAmount = 0;
    let newCompleted: number = null
    let newCompletedIndex: number = null

    if (!change.before.exists && change.after.exists) {
        // if creating new task
        totalChangeAmount++;
        const afterCompleted = change.after.data["isCompleted"] as Boolean
        if (afterCompleted) {
            completedChangeAmount++;
        } else {
            activeChangeAmount++;
        }
    } else if (change.before.exists && !change.after.exists) {
        // if delete old task
        totalChangeAmount--;
        const beforeCompleted = change.before.data["isCompleted"] as Boolean
        if (beforeCompleted) {
            completedChangeAmount--;
        } else {
            activeChangeAmount--;
        }
    } else {
        // both exits => update complete
        const beforeCompleted = change.before.data["isCompleted"] as Boolean
        const afterCompleted = change.after.data["isCompleted"] as Boolean
        if (beforeCompleted !== afterCompleted) {
            // change complete
            if (beforeCompleted) {
                // task isCompleted change from true to false
                activeChangeAmount++;
                completedChangeAmount--;
            } else {
                // task isCompleted change from false to true
                activeChangeAmount--;
                completedChangeAmount++;
            }
        }
    }
    await firestore.runTransaction(async (transaction) => {
        if (totalChangeAmount !== 0) {
            const result = await getTotalCount(totalShardRef, transaction)
            newTotal = result.oldCount + totalChangeAmount
            newTotalIndex = result.shardId
        }
        if (activeChangeAmount !== 0) {
            const result = await getTotalCount(activeShardRef, transaction)
            newActive = result.oldCount + activeChangeAmount
            newActiveIndex = result.shardId
        }
        if (completedChangeAmount !== 0) {
            const result = await getTotalCount(completedShardRef, transaction)
            newCompleted = result.oldCount + completedChangeAmount
            newCompletedIndex = result.shardId
        }
        if (newTotalIndex !== null) {
            await updateShard(totalShardRef, newTotalIndex, newTotal, transaction);
        }
        if (newActiveIndex !== null) {
            await updateShard(totalShardRef, newActiveIndex, newActive, transaction);
        }
        if (newCompletedIndex !== null) {
            await updateShard(totalShardRef, newCompletedIndex, newCompleted, transaction);
        }
    })

})

async function getTotalCount(
    shardCollectionRef: FirebaseFirestore.CollectionReference,
    transaction: FirebaseFirestore.Transaction,
): Promise<{ shardId: number, oldCount: number }> {
    const shardId = Math.floor(Math.random() * numberOfShard);
    const shardRef = shardCollectionRef.doc(shardId.toString());
    const shardSnapshot = await transaction.get(shardRef)
    const oldCount = shardSnapshot.get("count") as number

    return { shardId: shardId, oldCount: oldCount }
}
async function updateShard(
    shardCollectionRef: FirebaseFirestore.CollectionReference,
    shardIndex: number,
    newAmount: number,
    transaction: FirebaseFirestore.Transaction,
) {
    const shardRef = shardCollectionRef.doc(shardIndex.toString());
    await transaction.set(shardRef, { count: newAmount }, { merge: true })
}

async function initShard(transaction: FirebaseFirestore.Transaction, collectionRef: FirebaseFirestore.CollectionReference) {
    const totalShardDocs = await transaction.get(collectionRef)
    const docSize: number = totalShardDocs.size;
    if (docSize === 0) {
        for (let i = 0; i < numberOfShard; i++) {
            const shardRef = collectionRef.doc(i.toString());
            transaction.set(shardRef, { count: 0 });
        }
    }
}


// TODO config searchableAttr
// TODO optimize update index only if content if diff
export const indexTask = functions.firestore.document('user/{userId}/task/{taskId}').onWrite(async (snap, context) => {
    const beforeSnapshot = snap.before;
    const afterSnapshot = snap.after;
    const userId = context.params["userId"] // replace with auth
    const taskIndex = client.initIndex(userId + taskIndexPostFix)
    console.log(`index name is ${userId + taskIndexPostFix}`)
    // if delete
    if (afterSnapshot.exists === false) {
        console.log(`delete index ${beforeSnapshot.id}`)
        await taskIndex.deleteObject(beforeSnapshot.id)
    } else {
        // ether create new or update
        const index = {
            objectID: afterSnapshot.id,
            title: afterSnapshot.data().title,
            description: afterSnapshot.data().description
        };
        console.log(`index to add is ${index}`)
        await taskIndex.addObject(index)
    }
});

export const searchTask = functions.https.onCall(async (data, context) => {
    const query: string = data.query;
    const pageSize: number = data.pageSize;
    const page: number = data.page
    const userId = "yTKBsO7PrtkY8W5dtPyr" // TODO replace with auth
    const index = client.initIndex(userId + taskIndexPostFix)
    const result = await index.search({
        query: query,
        page: page,
        hitsPerPage: pageSize
    })
    return result
})

// export const search = functions.https.onRequest((request, response) => {
//     response.send("Hello from Firebase!");
//     notesIndex.search("")
// });

app.get('/search/:keyword', async (req, res) => {
    const keyword: String = req.params.keyword;
    const userId = "yTKBsO7PrtkY8W5dtPyr" // TODO replace with auth
    const index = client.initIndex(userId + taskIndexPostFix)
    const result = await index.search(keyword)
    res.send(result)
});

export const task = functions.https.onRequest(app);
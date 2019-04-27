set -e

stop="./gradlew --stop"
build="./gradlew :frontend:android:app:assembleDebug  :frontend:android:dynamic:data:dataTask:dataTaskRoom:assembleDebug :frontend:android:dynamic:data:dataTask:dataTaskFirestore:assembleDebug :frontend:android:dynamic:leak:assembleDebug :frontend:android:dynamic:dataImplementation:assembleDebug :frontend:android:dynamic:data:dataTask:dataTaskSqlDelight:assembleDebug :frontend:android:dynamic:seedDatabase:assembleDebug :frontend:android:dynamic:settings:assembleDebug -PminSdk=16 --stacktrace --offline"
uninstall="adb uninstall com.sample.todo.debug"
install="adb install-multiple frontend/android/app/build/outputs/apk/debug/app-debug.apk  frontend/android/dynamic/data/dataTask/dataTaskFirestore/build/outputs/apk/debug/dataTaskFirestore-debug.apk  frontend/android/dynamic/data/dataTask/dataTaskRoom/build/outputs/apk/debug/dataTaskRoom-debug.apk  frontend/android/dynamic/data/dataTask/dataTaskSqlDelight/build/outputs/apk/debug/dataTaskSqlDelight-debug.apk  frontend/android/dynamic/dataImplementation/build/outputs/apk/debug/dataImplementation-debug.apk  frontend/android/dynamic/leak/build/outputs/apk/debug/leak-debug.apk  frontend/android/dynamic/seedDatabase/build/outputs/apk/debug/seedDatabase-debug.apk  frontend/android/dynamic/settings/build/outputs/apk/debug/settings-debug.apk"

run="adb shell am start -n 'com.sample.todo.debug/com.sample.todo.splash.SplashActivity'"

$stop && $build && $uninstall && $install && $run

echo "Total time: $SECONDS secs"

now=$(date)
echo "$now"
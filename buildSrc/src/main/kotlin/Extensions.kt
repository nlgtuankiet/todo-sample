
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.gradle.api.invocation.Gradle
import java.io.File

val log = mutableListOf<String>()

// TODO implement project core to make this proguardable
fun AllOpenExtension.enableIfInUnitTest(gradle: Gradle) {
    val unitTestName = getUnitTestTaskName(gradle)
    if (unitTestName != null) {
        log.add("Enabled kotlin-allopen for com.sample.todo.core.Mockable because of gradle task name: $unitTestName")
        annotation("com.sample.todo.core.Mockable")
    }
}

fun Gradle.isInUnitTest(): Boolean {
    return getUnitTestTaskName(this) != null
}

private fun getUnitTestTaskName(gradle: Gradle): String? {
    return gradle.startParameter.taskNames.firstOrNull { name ->
        // for command line e.g. gradlew test
        name == "test"
            // for Android Studio e.g. right click in test class -> Run 'FooTest'
            // or right click on package -> Run 'Test in 'Foo''
            || name.endsWith("UnitTestSources")
    }
}

fun Gradle.todoReport() {
    println("Gradle TODO report:")
    println("--------------------")
    for (s in log) {
        println(s)
    }
    println("--------------------")

}
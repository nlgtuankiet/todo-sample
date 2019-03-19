plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

buildscript {
    dependencies {
        classpath(Libs.kotlin_gradle_plugin)
    }
}

dependencies {
    implementation(Libs.dagger)
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.lorem)
    implementation(Libs.paging_common)
    implementation(Libs.rxjava)
    implementation(Libs.rxkotlin)
    kapt(Libs.dagger_compiler)
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.mockito_kotlin)
    implementation("org.threeten:threetenbp:1.3.8:no-tzdb")
}

kapt {
    arguments {
        arg("dagger.formatGeneratedSource", "disabled")
        arg("dagger.gradle.incremental", "enabled")
    }
    javacOptions {
        // Increase the max count of errors from annotation processors.
        // Default is 100.
        option("-Xmaxerrs", 500)
    }
    // not sure
    useBuildCache = true
}
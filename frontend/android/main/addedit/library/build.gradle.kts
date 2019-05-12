plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.javax_inject)
    implementation(Libs.rxjava)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.threetenbp)
    implementation(project(":frontend:android:domain"))
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.mockito_kotlin)
    testImplementation(Libs.kotlinx_coroutines_core)
}

kapt {
    arguments {
        arg("dagger.formatGeneratedSource", "disabled")
        arg("dagger.gradle.incremental", "enabled")
    }
    javacOptions {
        option("-Xmaxerrs", 500)
    }
    // not sure
    useBuildCache = true
}

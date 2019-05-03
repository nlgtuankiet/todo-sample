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

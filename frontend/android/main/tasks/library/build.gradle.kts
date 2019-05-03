plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.javax_inject)
    implementation(Libs.rxjava)
    implementation(Libs.paging_runtime_ktx)
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

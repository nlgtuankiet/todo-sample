plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Config.compileSdk)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    defaultConfig {
        minSdkVersion(Config.minSdk)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isDebuggable = Config.isReleaseDebugable
            isMinifyEnabled = false
        }
    }
}

dependencies {
    // projects
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(project(":common"))
    implementation(project(":app"))
    api(project(":data-core"))
    implementation(project(":data-preference"))

    implementation(Libs.kotlin_stdlib_jdk8)

    implementation(Libs.dagger_android_support)
    kapt(Libs.dagger_android_processor)
    kapt(Libs.dagger_compiler)

    implementation(Libs.timber)
    implementation(Libs.core_ktx)

    testImplementation(Libs.robolectric)
    testImplementation(Libs.rxjava)
    testImplementation(Libs.core_testing)
    testImplementation(Libs.androidx_test_core)
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.mockito_kotlin)
    testImplementation(Libs.paging_common)

    testImplementation(Libs.room_testing)

    androidTestImplementation(Libs.mockito_android)
    androidTestImplementation(Libs.mockito_core)
    androidTestImplementation(Libs.androidx_test_core)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
    testImplementation(project(":testShared"))

    implementation(Libs.work_runtime_ktx)
    implementation(Libs.work_testing)
}
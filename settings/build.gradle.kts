plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    dataBinding {
        isEnabled = true
    }
    compileSdkVersion(28)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    defaultConfig {
        minSdkVersion(21)
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
    kapt(Libs.room_compiler)

    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui_ktx)

    implementation(Libs.dagger_android_support)
    compileOnly(Libs.assisted_inject_annotations_dagger2)
    kapt(Libs.assisted_inject_processor_dagger2)
    kapt(Libs.dagger_android_processor)
    kapt(Libs.dagger_compiler)

    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.fragment_ktx)

    implementation(Libs.constraintlayout)

    implementation(Libs.room_coroutines)
    implementation(Libs.room_runtime)
    implementation(Libs.room_rxjava2)

    implementation(Libs.timber)
    implementation(Libs.paging_rxjava2_ktx)
    implementation(Libs.core_ktx)

    testImplementation(Libs.robolectric)
    testImplementation(Libs.rxjava)
    testImplementation(Libs.core_testing)
    testImplementation(Libs.androidx_test_core)
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.mockito_kotlin)
    testImplementation(Libs.paging_common)

    implementation(Libs.activity_ktx)
    implementation(Libs.appcompat)


    testImplementation(Libs.room_testing)

    androidTestImplementation(Libs.mockito_android)
    androidTestImplementation(Libs.mockito_core)
    androidTestImplementation(Libs.androidx_test_core)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
    testImplementation(project(":testShared"))
}
import org.gradle.kotlin.dsl.*

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
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
    implementation(Libs.kotlin_stdlib_jdk8)
    kapt(Libs.room_compiler)

    implementation(Libs.dagger)
    kapt(Libs.dagger_compiler)

    implementation(Libs.room_coroutines)
    implementation(Libs.room_runtime)
    implementation(Libs.room_rxjava2)
    implementation(project(":core"))
    implementation(project(":domain"))
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
    testImplementation(project(":testShared"))

    testImplementation(Libs.room_testing)

    androidTestImplementation(Libs.mockito_android)
    androidTestImplementation(Libs.mockito_core)
    androidTestImplementation(Libs.androidx_test_core)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
    implementation(project(":data-core"))
//    androidTestImplementation(Libs.espresso_core)
//    androidTestImplementation(Libs.mockito_kotlin)
//    androidTestImplementation(project(":testShared"))
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

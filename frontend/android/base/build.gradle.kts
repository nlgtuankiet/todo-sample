plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Android.compileSdkVersion)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dataBinding {
        isEnabled = true
    }
    defaultConfig {
        minSdkVersion(Android.minSdkVersion)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = false
        }
    }
    lintOptions {
        setCheckReleaseBuilds(false)
    }
}

dependencies {
    implementation(Libs.com_google_android_play_core)
    androidTestImplementation(Libs.androidx_test_core)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
    androidTestImplementation(Libs.mockito_android)
    androidTestImplementation(Libs.mockito_core)
    implementation(Libs.core_ktx)
    implementation(Libs.kotlin_stdlib_jdk8)
    testImplementation(Libs.androidx_test_core)
    testImplementation(Libs.core_testing)
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.mockito_kotlin)
    testImplementation(Libs.robolectric)
    implementation(project(":frontend:android:core"))
    implementation(Libs.work_runtime_ktx)
    implementation(Libs.timber)
    implementation(Libs.dagger_android_support)
    implementation(Libs.mvrx)
    implementation(Libs.material)
    implementation(Libs.navigation_fragment_ktx)
    kapt(Libs.dagger_android_processor)
    kapt(Libs.dagger_compiler)
    // todo trim
    testImplementation("org.threeten:threetenbp:1.3.8")
    api("org.threeten:threetenbp:1.3.8:no-tzdb")
    api("com.jakewharton.threetenabp:threetenabp:1.2.0")
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

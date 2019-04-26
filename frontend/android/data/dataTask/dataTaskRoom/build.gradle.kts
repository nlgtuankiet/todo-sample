plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(Libs.work_runtime_ktx)
    androidTestImplementation(Libs.androidx_test_core)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
    androidTestImplementation(Libs.mockito_android)
    androidTestImplementation(Libs.mockito_core)
    api(project(":frontend:android:data:dataCore"))
    implementation(project(":frontend:android:data"))
    implementation(Libs.android_driver)
    implementation(Libs.android_paging_extensions)
    implementation(Libs.com_google_android_play_core)
    implementation(Libs.com_squareup_sqldelight_runtime)
    implementation(Libs.core_ktx)
    implementation(Libs.dagger)
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.paging_rxjava2_ktx)
    implementation(Libs.room_coroutines)
    implementation(Libs.room_runtime)
    implementation(Libs.room_rxjava2)
//    implementation("androidx.room:room-ktx:2.1.0-alpha07")
    implementation(Libs.rxjava2_extensions)
    implementation(Libs.timber)
    implementation(project(":frontend:android:data:dataPreference"))
    implementation(project(":frontend:android:domain"))
    kapt(Libs.dagger_compiler)
    kapt(Libs.room_compiler)
    testImplementation(Libs.androidx_test_core)
    testImplementation(Libs.core_testing)
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.mockito_kotlin)
    testImplementation(Libs.paging_common)
    testImplementation(Libs.robolectric)
    testImplementation(Libs.room_testing)
    testImplementation(Libs.rxjava)
    testImplementation(Libs.sqlite_driver)
    testImplementation(Libs.sqlite_jdbc)
    implementation("org.threeten:threetenbp:1.3.8:no-tzdb")
    implementation("com.google.firebase:firebase-firestore:18.1.0")
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.moshi:moshi:1.8.0")
    implementation("com.google.firebase:firebase-functions:16.3.0")
    compileOnly(Libs.assisted_inject_annotations_dagger2)
    kapt(Libs.assisted_inject_processor_dagger2)
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.8.0")
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

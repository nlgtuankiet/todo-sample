plugins {
    id("com.android.application")
    id("com.github.triplet.play") version "2.1.0"
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("io.fabric")
    id("kotlin-allopen")
}

allOpen {
    enableIfInUnitTest(gradle)
}

android {
    compileSdkVersion(28)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dataBinding {
        isEnabled = true
    }
    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        if (file("../release.keystore").exists()) {
            create("release") {
                storeFile = rootProject.file("release.keystore")
                storePassword = System.getenv("ANDROID_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("ANDROID_KEYSTORE_ALIAS")
                keyPassword = System.getenv("ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")
            }
        }
    }
    defaultConfig {
        applicationId = "com.sample.todo"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.1.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            resValue("string", "app_name", "Todo Dev")
            applicationIdSuffix = ".debug"
            signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
        getByName("release") {
            resValue("string", "app_name", "Todo")
            signingConfig = if (file("../release.keystore").exists()) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("debug")
            }
            isDebuggable = false
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    testOptions {
        unitTests.apply {
            isIncludeAndroidResources = true
        }
    }
}

play {
    // File 'todo-sample\app\build\outputs\bundle\release\app.aab' specified for property 'bundle' does not exist.
    // gradlew promoteReleaseArtifact --from-track beta --track production
    fromTrack = "beta"
    track = "beta"
    serviceAccountCredentials = file("service-account-key.json")
    defaultToAppBundles = true
    resolutionStrategy = "auto"
}

// TODO Assisted Inject compileOnly or implementation?
// TODO Lifecycle (ViewModel and LiveData) research lifecycle-livedata-ktx lifecycle-viewmodel-ktx
dependencies {
    androidTestImplementation(Libs.mockito_android)
    androidTestImplementation(Libs.mockito_core)
    androidTestImplementation(Libs.androidx_test_core)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.mockito_kotlin)
    androidTestImplementation(project(":testShared"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.activity_ktx)
    implementation(Libs.appcompat)
    implementation(Libs.constraintlayout)
    implementation(Libs.core_ktx)
    implementation(Libs.crashlytics)

    implementation(Libs.firebase_core)
    implementation(Libs.fragment_ktx)
    implementation(Libs.fragment_testing)
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.kotlinx_coroutines_android)
    implementation(Libs.kotlinx_coroutines_core)
//    implementation(Libs.leakcanary_android)
//    implementation(Libs.leakcanary_support_fragment)

    implementation(Libs.lorem)
    implementation(Libs.material)
    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui_ktx)

    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_reactivestreams_ktx)

    // paging
    implementation(Libs.paging_runtime_ktx)
    implementation(Libs.paging_rxjava2_ktx)

    // room
    implementation(Libs.room_coroutines)
    implementation(Libs.room_rxjava2)
    kapt(Libs.room_compiler)

    implementation(Libs.stetho)
    implementation(Libs.timber)
    implementation(Libs.work_runtime_ktx)
    implementation(Libs.work_testing)


    implementation(Libs.rxandroid)
    implementation(Libs.rxjava)
    testImplementation(Libs.rxjava)

    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Inject
    implementation(Libs.dagger_android_support)
    compileOnly(Libs.assisted_inject_annotations_dagger2)
    kapt(Libs.assisted_inject_processor_dagger2)
    kapt(Libs.dagger_android_processor)
    kapt(Libs.dagger_compiler)

//    releaseImplementation(Libs.leakcanary_android_no_op)
    testImplementation(Libs.robolectric)
    testImplementation(Libs.core_testing)
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.mockito_kotlin)
    testImplementation(Libs.paging_common)
    testImplementation(project(":testShared"))
}

kapt {
    arguments {
        arg("dagger.formatGeneratedSource", "disabled")
        arg("dagger.gradle.incremental", "enabled")
    }
    javacOptions {
        option("-Xmaxerrs", 500)
    }
    useBuildCache = true
}

apply(mapOf("plugin" to "com.google.gms.google-services"))

android.buildTypes.all {
    if (!file("google-services.json").exists()) {
        println("google-services.json not found, copy and rename from example")
        copy {
            from(".")
            into(".")
            include("google-services.json-example")
            rename("google-services.json-example", "google-services.json")
        }
    }
    if (!file("service-account-key.json").exists()) {
        println("service-account-key.json not found, copy and rename from example")
        copy {
            from(".")
            into(".")
            include("service-account-key.json-example")
            rename("service-account-key.json-example", "service-account-key.json")
        }
    }
}


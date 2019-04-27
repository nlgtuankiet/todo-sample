plugins {
    id("com.android.application")
    id("com.github.triplet.play") version "2.1.0"
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("io.fabric")
    id("kotlin-allopen")
}

allOpen {
    enableIfInUnitTest(gradle)
}

android {
    dexOptions {
        javaMaxHeapSize = "4g"
    }
    compileSdkVersion(Android.compileSdkVersion)
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
        val minSdk = run {
            if (project.hasProperty("minSdk")) {
                project.property("minSdk").toString().toIntOrNull() ?: Android.minSdkVersion
            } else {
                Android.minSdkVersion
            }
        }
        minSdkVersion(Android.minSdkVersion)
        if (minSdk != Android.minSdkVersion) {
            minSdkVersion(minSdk)
        }
        targetSdkVersion(Android.targetSdkVersion)
        versionCode = 56
        versionName = "1.2.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            signingConfig = if (file("../release.keystore").exists()) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("debug")
            }
            isDebuggable = false
            isMinifyEnabled = true
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
    lintOptions {
        setCheckReleaseBuilds(false)
    }
    testBuildType = "release"
    dynamicFeatures.add(":frontend:android:dynamic:settings")
    dynamicFeatures.add(":frontend:android:dynamic:seedDatabase")
    dynamicFeatures.add(":frontend:android:dynamic:leak")
    dynamicFeatures.add(":frontend:android:dynamic:dataImplementation")
    dynamicFeatures.add(":frontend:android:dynamic:data:dataTask:dataTaskFirestore")
    dynamicFeatures.add(":frontend:android:dynamic:data:dataTask:dataTaskRoom")
    dynamicFeatures.add(":frontend:android:dynamic:data:dataTask:dataTaskSqlDelight")

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
dependencies {
    androidTestImplementation(Libs.androidx_test_core)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.mockito_android)
    androidTestImplementation(Libs.mockito_core)
    androidTestImplementation(Libs.mockito_kotlin)
    androidTestImplementation(Libs.core_testing)
    compileOnly(Libs.assisted_inject_annotations_dagger2)
    implementation(Libs.activity_ktx)
    implementation(Libs.appcompat)
    implementation(Libs.com_google_android_play_core)
    implementation(Libs.constraintlayout)
    implementation(Libs.core_ktx)
    implementation(Libs.crashlytics)
    implementation(Libs.dagger_android_support)
    implementation(Libs.epoxy)
    implementation(Libs.epoxy_databinding)
    implementation(Libs.epoxy_paging)
    implementation(Libs.firebase_core)
    implementation(Libs.fragment_ktx)
    implementation(Libs.fragment_testing)
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.kotlinx_coroutines_android)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.lifecycle_reactivestreams_ktx)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lorem)
    implementation(Libs.material)
    implementation(Libs.mvrx)
    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui_ktx)
    implementation(Libs.paging_runtime_ktx)
    implementation(Libs.paging_rxjava2_ktx)
    implementation(Libs.room_coroutines)
    implementation(Libs.room_rxjava2)
    implementation(Libs.rxandroid)
    implementation(Libs.rxjava)
    implementation(Libs.stetho)
    implementation(Libs.timber)
    implementation(Libs.work_runtime_ktx)
    implementation(Libs.work_testing)

    kapt(Libs.assisted_inject_processor_dagger2)
    kapt(Libs.dagger_android_processor)
    kapt(Libs.dagger_compiler)
    kapt(Libs.epoxy_processor)
    kapt(Libs.room_compiler)
    testImplementation(Libs.core_testing)
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.mockito_kotlin)
    testImplementation(Libs.paging_common)
    testImplementation(Libs.robolectric)
    testImplementation(Libs.rxjava)

    implementation(project(":frontend:android:ui:main"))
    implementation(project(":frontend:android:ui:main:mainNavigation"))
    implementation(project(":frontend:android:ui:splash"))
    implementation(project(":frontend:android:work"))
    implementation(project(":frontend:android:base"))
    implementation(project(":frontend:android:ui"))
    implementation(project(":frontend:android:data"))
    implementation(project(":frontend:android:data:dataPreference"))

    implementation(project(":frontend:android:domain"))
    implementation(project(":frontend:android:downloadModule"))

    implementation("androidx.multidex:multidex:2.0.1")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.0-alpha-1")
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
    correctErrorTypes = true
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


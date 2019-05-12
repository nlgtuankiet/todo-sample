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
    packagingOptions {
        exclude("META-INF/library.kotlin_module")
        exclude("META-INF/atomicfu.kotlin_module")
    }
    defaultConfig {
        val minSdk = run {
            if (project.hasProperty("minSdk")) {
                project.property("minSdk").toString().toIntOrNull() ?: Android.minSdkVersion
            } else {
                Android.minSdkVersion
            }
        }
        minSdkVersion(Android.minSdkVersion)
        if (minSdk != Android.minSdkVersion && minSdk > 5) {
            minSdkVersion(minSdk)
        }
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
    androidTestImplementation(Libs.androidx_test_core)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
    androidTestImplementation(Libs.mockito_android)
    androidTestImplementation(Libs.mockito_core)
    implementation(Libs.core_ktx)
    implementation(Libs.dagger)
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.paging_rxjava2_ktx)
    implementation(Libs.room_coroutines)
    implementation(Libs.room_runtime)
    implementation(Libs.room_rxjava2)
    implementation(Libs.timber)
    implementation(project(":frontend:android:data:dataCore"))
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

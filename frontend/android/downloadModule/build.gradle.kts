plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Android.compileSdkVersion)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    implementation(Libs.android_paging_extensions)
    implementation(Libs.core_ktx)
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.timber)
    testImplementation(Libs.androidx_test_core)
    testImplementation(Libs.core_testing)
    testImplementation(Libs.junit_junit)
    testImplementation(Libs.mockito_kotlin)
    testImplementation(Libs.robolectric)
    implementation(project(":frontend:android:domain"))
    implementation(project(":frontend:android:work"))
    implementation(project(":frontend:android:main:mainNavigation"))
    implementation(project(":frontend:android:downloadModule:library"))
    implementation(Libs.epoxy)
    implementation(Libs.epoxy_databinding)
    implementation(Libs.epoxy_paging)
    kapt(Libs.epoxy_processor)
    implementation(Libs.mvrx)
    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui_ktx)
    implementation(project(":frontend:android:base"))
    implementation(Libs.navigation_fragment_ktx)
    compileOnly(Libs.assisted_inject_annotations_dagger2)
    kapt(Libs.assisted_inject_processor_dagger2)
    kapt(Libs.dagger_android_processor)
    kapt(Libs.dagger_compiler)
    implementation(Libs.dagger_android_support)
    implementation(Libs.constraintlayout)
    kapt(Libs.epoxy_processor)
    implementation(Libs.epoxy)
    implementation(Libs.epoxy_databinding)
    implementation(Libs.epoxy_paging)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.work_runtime_ktx)
    implementation(Libs.work_testing)
    implementation(Libs.com_google_android_play_core)
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

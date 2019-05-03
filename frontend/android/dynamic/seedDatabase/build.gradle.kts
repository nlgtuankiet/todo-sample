plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Android.compileSdkVersion)
    dataBinding {
        isEnabled = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
}

dependencies {
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.timber)
    implementation(project(":frontend:android:app"))
    implementation(project(":frontend:android:domain"))
    implementation(project(":frontend:android:data"))
    kapt(Libs.epoxy_processor)
    implementation(Libs.mvrx)
    implementation(project(":frontend:android:base"))
    compileOnly(Libs.assisted_inject_annotations_dagger2)
    kapt(Libs.assisted_inject_processor_dagger2)
    kapt(Libs.dagger_android_processor)
    kapt(Libs.dagger_compiler)
    implementation(Libs.dagger_android_support)
    implementation(Libs.constraintlayout)
    kapt(Libs.epoxy_processor)
    implementation(Libs.epoxy)
    implementation(Libs.epoxy_paging)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.fragment_ktx)
    implementation(Libs.activity_ktx)
    implementation(Libs.work_runtime_ktx)
    implementation(Libs.work_testing)
    implementation(project(":frontend:android:work"))
    implementation(project(":frontend:android:dynamic:seedDatabase:library"))
    implementation("com.google.guava:listenablefuture:1.0")
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

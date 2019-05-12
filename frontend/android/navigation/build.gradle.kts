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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    implementation(Libs.core_ktx)
    implementation(Libs.javax_inject)
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.navigation_fragment_ktx)
    implementation(project(":frontend:android:base"))
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

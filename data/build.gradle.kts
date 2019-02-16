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
    implementation(project(":common"))
    api(project(":data-core"))
    implementation(project(":data-preference"))
    implementation("com.squareup.sqldelight:android-driver:1.0.3")
    implementation("com.squareup.sqldelight:runtime:1.0.3")
    implementation("com.squareup.sqldelight:rxjava2-extensions:1.0.3")
    implementation("com.squareup.sqldelight:android-paging-extensions:1.0.3")
    testImplementation("com.squareup.sqldelight:sqlite-driver:1.0.3")
    testImplementation("org.xerial:sqlite-jdbc:3.25.2")

    testImplementation(Libs.room_testing)

    androidTestImplementation(Libs.mockito_android)
    androidTestImplementation(Libs.mockito_core)
    androidTestImplementation(Libs.androidx_test_core)
    androidTestImplementation(Libs.androidx_test_ext_junit)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.core_testing)
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

//tasks.register("cleanGenerate") {
//    group = "sqldelight123"
//    doLast {
//
//    }
//    setFinalizedBy(listOf(":common:clean", ":common:generateSqlDelightInterface"))
//}
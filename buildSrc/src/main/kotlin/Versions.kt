import kotlin.String

/**
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version. */
object Versions {
    const val android_arch_navigation: String = "1.0.0-alpha11" 

    const val android_arch_work: String = "1.0.0-rc02"

    const val activity_ktx: String = "1.0.0-alpha03" 

    const val appcompat: String = "1.0.2" 

    const val core_testing: String = "2.0.0" 

    const val constraintlayout: String = "2.0.0-alpha3" 

    const val core_ktx: String = "1.0.1" 

    const val androidx_databinding: String = "3.5.0-alpha02"

    const val androidx_fragment: String = "1.1.0-alpha03" 

    const val androidx_lifecycle: String = "2.1.0-alpha01" 

    const val androidx_paging: String = "2.1.0" 

    const val androidx_room: String = "2.1.0-alpha04" 

    const val espresso_core: String = "3.1.0" // available: "3.1.1"

    const val androidx_test_ext_junit: String = "1.0.0" // available: "1.1.0"

    const val androidx_test_core: String = "1.0.0" // available: "1.1.0"

    const val androidx_test_rules: String = "1.1.0" // available: "1.1.1"

    const val androidx_test_runner: String = "1.1.0" // available: "1.1.1"

    const val aapt2: String = "3.5.0-alpha02-5228738" 

    const val com_android_tools_build_gradle: String = "3.3.0"

    const val lint_gradle: String = "26.5.0-alpha02" 

    const val crashlytics: String = "2.9.7" // available: "2.9.9"

    const val com_diffplug_gradle_spotless_gradle_plugin: String = "3.17.0" // available: "3.18.0"

    const val stetho: String = "1.5.0" 

    const val com_github_triplet_play_gradle_plugin: String = "2.1.0" 

    const val material: String = "1.1.0-alpha02" 

    const val com_google_dagger: String = "2.21" 

    const val firebase_core: String = "16.0.5" // available: "16.0.7"

    const val google_services: String = "4.2.0" 

    const val com_gradle_build_scan_gradle_plugin: String = "2.1" 

    const val timber: String = "4.7.1" 

    const val mockito_kotlin: String = "2.1.0" 

    const val com_squareup_inject: String = "0.3.2" 

    const val android_driver: String = "1.0.3" 

    const val android_paging_extensions: String = "1.0.3" 

    const val gradle_plugin: String = "1.0.3" 

    const val com_squareup_sqldelight_runtime: String = "none" 

    const val rxjava2_extensions: String = "1.0.3" 

    const val sqlite_driver: String = "1.0.3" 

    const val lorem: String = "2.1" 

    const val de_fayard_buildsrcversions_gradle_plugin: String = "0.3.2" 

    const val io_fabric_tools_gradle: String = "1.27.0" // available: "1.27.1"

    const val rxandroid: String = "2.1.0" 

    const val rxjava: String = "2.2.6" 

    const val rxkotlin: String = "2.3.0" 

    const val javax_inject: String = "1" 

    const val junit_junit: String = "4.12" 

    const val org_jetbrains_kotlin: String = "1.3.20" // available: "1.3.21"

    const val org_jetbrains_kotlinx: String = "1.1.1" 

    const val org_mockito: String = "2.23.18" // available: "2.24.4"

    const val robolectric: String = "4.1" 

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "5.1.1"

        const val currentVersion: String = "5.2.1"

        const val nightlyVersion: String = "5.3-20190211022529+0000"

        const val releaseCandidate: String = ""
    }
}

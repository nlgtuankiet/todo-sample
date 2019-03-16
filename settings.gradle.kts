rootProject.buildFileName = "build.gradle.kts"

include(":app")
include(":core")
include(":domain")
include(":data")
include(":common")
include(":dataPreference")
include(":dataCore")
include(":settings")

enableFeaturePreview("GRADLE_METADATA")

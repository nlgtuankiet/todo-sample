rootProject.buildFileName = "build.gradle.kts"

include(":frontend:android:app")
include(":frontend:android:work")
include(":frontend:android:downloadModule")
include(":frontend:android:navigation")

include(":frontend:android:domain")

include(":frontend:android:data")
include(":frontend:android:data:dataPreference")
include(":frontend:android:data:dataCore")
include(":frontend:android:data:dataTask:dataTaskRoom")
include(":frontend:android:data:dataTask:dataTaskFirestore")
include(":frontend:android:data:dataTask:dataTaskSqldelight")
include(":frontend:android:data:dataTask:dataTaskSqldelight:common")

include(":frontend:android:ui:main")
include(":frontend:android:ui:main:mainNavigation")
include(":frontend:android:ui:main:tasks")
include(":frontend:android:ui:main:taskdetail")
include(":frontend:android:ui:main:moduleDetail")
include(":frontend:android:ui:main:about")
include(":frontend:android:ui:main:addedit")
include(":frontend:android:ui:main:search")
include(":frontend:android:ui:main:statistics")

include(":frontend:android:ui:splash")

include(":frontend:android:base")

include(":frontend:android:dynamic:settings")
include(":frontend:android:dynamic:leak")
include(":frontend:android:dynamic:seedDatabase")

enableFeaturePreview("GRADLE_METADATA")

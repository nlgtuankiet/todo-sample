rootProject.buildFileName = "build.gradle.kts"

include(":frontend:android:app")
include(":frontend:android:work")
include(":frontend:android:downloadModule")
include(":frontend:android:downloadModule:library")
include(":frontend:android:navigation")

include(":frontend:android:domain")

include(":frontend:android:data")
include(":frontend:android:data:dataPreference")
include(":frontend:android:data:dataCore")


include(":frontend:android:main")
include(":frontend:android:main:mainNavigation")
include(":frontend:android:main:tasks")
include(":frontend:android:main:tasks:library")
include(":frontend:android:main:taskDetail")
include(":frontend:android:main:taskDetail:library")
include(":frontend:android:main:moduleDetail")
include(":frontend:android:main:about")
include(":frontend:android:main:about:library")
include(":frontend:android:main:addedit")
include(":frontend:android:main:addedit:library")
include(":frontend:android:main:search")
include(":frontend:android:main:search:library")
include(":frontend:android:main:statistics")
include(":frontend:android:main:statistics:library")

include(":frontend:android:splash")

include(":frontend:android:base")

include(":frontend:android:dynamic:settings")
include(":frontend:android:dynamic:leak")
include(":frontend:android:dynamic:seedDatabase")
include(":frontend:android:dynamic:seedDatabase:library")
include(":frontend:android:dynamic:dataImplementation")
include(":frontend:android:dynamic:data:dataTask:dataTaskFirestore")
include(":frontend:android:dynamic:data:dataTask:dataTaskSqlDelight")
include(":frontend:android:dynamic:data:dataTask:dataTaskSqlDelight:common")
include(":frontend:android:dynamic:data:dataTask:dataTaskRoom")

enableFeaturePreview("GRADLE_METADATA")

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    jcenter()
    google()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://dl.bintray.com/mockito/maven")
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.3.20")
//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.20")
}

//configurations.all {
//    resolutionStrategy.eachDependency {
//        if (requested.group == "org.jetbrains.kotlin" && requested.name == "kotlin-compiler-embeddable") {
//            useVersion("1.3.10")
//        }
//    }
//}
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.fabric.io/public")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/mockito/maven")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://kotlin.bintray.com/kotlinx/")
    }
    dependencies {
        classpath("com.jakewharton:butterknife-gradle-plugin:10.1.0")
        classpath("com.squareup.sqldelight:gradle-plugin:1.0.3")
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.navigation_safe_args_gradle_plugin)
        classpath(Libs.google_services)
        classpath(Libs.io_fabric_tools_gradle)
        classpath("org.jetbrains.kotlin:kotlin-allopen:${Versions.org_jetbrains_kotlin}")
    }
}

plugins {
    id("de.fayard.buildSrcVersions") version "0.3.2"
    id("project-report")
    id("com.diffplug.gradle.spotless") version "3.22.0"
    `build-scan`
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.fabric.io/public")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/mockito/maven")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://kotlin.bintray.com/kotlinx/")

    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Adagger.formatGeneratedSource=disabled".also {
                    println("config kapt lol")
                },
                "-AFORMAT_GENERATED_SOURCE=DISABLED"

            )
        }
    }
}
subprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.fabric.io/public")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
//        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/mockito/maven")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://kotlin.bintray.com/kotlinx/")
    }
}

//buildScan {
//    termsOfServiceUrl = "https://gradle.com/terms-of-service"
//    termsOfServiceAgree = "yes"
////    publishAlways()
//}

subprojects {
    apply(plugin = "com.diffplug.gradle.spotless")
    // TODO how to config kapt
    spotless {
        kotlin {
            trimTrailingWhitespace()
            endWithNewline()
            ignoreErrorForPath("./frontend/android/data/dataTask/dataTaskSqlDelight/common/build")
            target("**/*.kt")
            ktlint("0.31.0")
        }
        xml {
        }

    }
    tasks.withType<KotlinCompile>().configureEach {
        println("Configuring $name in project ${project.name}...")
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-XXLanguage:+InlineClasses",
                "-Xallow-result-return-type"
            )
            incremental = true
            jvmTarget = "1.6"
            languageVersion = "1.3"
            apiVersion = "1.3"
        }
    }
}

gradle.projectsEvaluated {
    todoReport()
}

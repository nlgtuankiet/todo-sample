import org.gradle.kotlin.dsl.*

plugins {
    id("java-library")
    id("kotlin")
}


dependencies {
    implementation(Libs.kotlin_stdlib_jdk8)
}

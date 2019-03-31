import org.gradle.kotlin.dsl.dependencies

plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation(Libs.javax_inject)
    implementation(Libs.kotlin_stdlib_jdk8)
}

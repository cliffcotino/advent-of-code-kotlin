
val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.findLibrary("kotlin-gradle").get())
}

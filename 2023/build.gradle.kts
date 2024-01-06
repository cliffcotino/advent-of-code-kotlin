val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("aoc.kotlin-conventions")
}

dependencies {
    implementation(libs.findLibrary("kotlin-logging").get())
    implementation(libs.findLibrary("kotlin-coroutines").get())
    implementation(project(":common"))
}

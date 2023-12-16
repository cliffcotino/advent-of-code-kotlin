plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
tasks {
    sourceSets {
        main {
            java.srcDirs("src/main/kotlin")
        }
    }

    wrapper {
        gradleVersion = "8.5"
    }
}

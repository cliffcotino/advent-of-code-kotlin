import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

val libs = versionCatalogs.named("libs")

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

kotlin {
    val jdkVersion = libs.findVersion("jdk").get().toString()
    val kotlinVersion = libs.findVersion("kotlin").get().toString()
        .substringBeforeLast(".").replace(".", "_")

    jvmToolchain {
        languageVersion.set(
            JavaLanguageVersion.of(jdkVersion)
        )
    }
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.valueOf("JVM_${jdkVersion}"))
        languageVersion.set(
            KotlinVersion.valueOf("KOTLIN_${kotlinVersion}")
        )
        apiVersion.set(
            KotlinVersion.valueOf("KOTLIN_${kotlinVersion}")
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxParallelForks = Runtime.getRuntime().availableProcessors()
}

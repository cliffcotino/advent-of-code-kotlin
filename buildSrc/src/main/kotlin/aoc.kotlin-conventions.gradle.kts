import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    kotlin("jvm")
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

kotlin {
    jvmToolchain {
        languageVersion.set(
            JavaLanguageVersion.of(libs.findVersion("jdk").get().toString())
        )
    }
    compilerOptions {
        val kotlinVersion = libs.findVersion("kotlin").get().toString()
            .substringBeforeLast(".").replace(".", "_")
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.valueOf("JVM_${libs.findVersion("jdk").get()}"))
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

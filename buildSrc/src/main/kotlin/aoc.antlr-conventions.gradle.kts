import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.antlr
import org.gradle.kotlin.dsl.getByType

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    antlr
    id("aoc.kotlin-conventions")
}

dependencies {
    antlr("org.antlr:antlr4:${libs.findVersion("antlr").get()}")
    implementation(libs.findLibrary("antlr-runtime").get())
}

tasks.named("compileJava").configure {
    dependsOn("generateGrammarSource")
}

tasks.named("compileTestJava").configure {
    dependsOn("generateTestGrammarSource")
}

tasks.named("compileKotlin").configure {
    dependsOn("generateGrammarSource")
}

tasks.named("compileTestKotlin").configure {
    dependsOn("generateTestGrammarSource")
}

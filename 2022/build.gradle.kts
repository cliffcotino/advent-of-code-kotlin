plugins {
    antlr
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.13.0") // use ANTLR version 4
    implementation("org.antlr:antlr4-runtime:4.13.0")
    implementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation("org.jetbrains.kotlin:kotlin-test-junit5:1.8.10")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
}

tasks.named("compileJava").configure {
    dependsOn("generateGrammarSource")
}

tasks.named("compileKotlin").configure {
    dependsOn("generateGrammarSource")
}

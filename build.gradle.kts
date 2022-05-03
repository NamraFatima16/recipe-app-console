import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    application
    id("org.jetbrains.dokka") version "1.6.10" // Plugin for Dokka
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1" // Plugin for Ktlint
    jacoco // Plugin for code coverage report
}

group = "me.namra"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.thoughtworks.xstream:xstream:1.4.19")
    implementation("org.codehaus.jettison:jettison:1.4.1")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.6.20")
    // Dependencies for logging
    implementation("io.github.microutils:kotlin-logging:2.1.21")
    implementation("org.slf4j:slf4j-simple:1.7.36")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

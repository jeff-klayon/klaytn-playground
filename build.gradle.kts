import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    id("org.jetbrains.kotlin.jvm") version "1.5.20" apply false
    idea
}

allprojects {
    group = "com.krustuniverse.klayon"
    version = "1.0.0-SNAPSHOT"

    tasks.withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

idea {
    module {
        isDownloadSources = true
    }
}

configurations {
    all {
        exclude("junit", "junit")
        exclude("org.slf4j", "slf4j-log4j12")
        exclude("log4j", "log4j")
        exclude("commons-logging", "commons-logging")
    }
}

val jacksonVersion by extra { "2.12.4" }
val junitJupiterVersion by extra { "5.7.2" }
val junitPlatformVersion by extra { "1.7.2" }
val mockitoVersion by extra { "3.11.2" }
val mockitoKotlinVersion by extra { "2.2.0" }
val logbackVersion by extra { "1.2.3" }
val slf4jVersion by extra { "1.7.31" }

apply(plugin = "org.jetbrains.kotlin.jvm")

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.klaytn.caver:core:1.6.3")

    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("commons-io:commons-io:2.10.0")
    implementation("commons-codec:commons-codec:1.15")
    implementation("com.google.guava:guava:30.1.1-jre")

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:log4j-over-slf4j:$slf4jVersion")
    implementation("org.slf4j:jcl-over-slf4j:$slf4jVersion")
    implementation("org.slf4j:jul-to-slf4j:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.14.0")
    implementation("ch.qos.logback:logback-core:$logbackVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion")

    testImplementation("org.mockito:mockito-inline:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion")
}

tasks {
    test {
        useJUnitPlatform()
        reports {
            html.required.set(false)
        }
    }
}

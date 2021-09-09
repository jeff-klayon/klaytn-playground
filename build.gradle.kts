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
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/krustuniverse-klayon/packages")
            credentials {
                username = "jeff-klayon"
                password = "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"
            }
        }
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

val junitJupiterVersion by extra { "5.7.2" }
val junitPlatformVersion by extra { "1.7.2" }
val mockitoVersion by extra { "3.11.2" }
val mockitoKotlinVersion by extra { "2.2.0" }

apply(plugin = "org.jetbrains.kotlin.jvm")

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.krustuniverse.klayon:commons-utils:1.0.0-SNAPSHOT")

    implementation("com.klaytn.caver:core:1.6.3")

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

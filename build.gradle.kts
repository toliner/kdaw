plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
}

group = "dev.kotx"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
    implementation("io.ktor:ktor-client-core:1.6.0")
    implementation("io.ktor:ktor-client-okhttp:1.6.0")
    implementation("io.ktor:ktor-client-websockets:1.6.0")
    implementation("io.ktor:ktor-client-serialization:1.6.0")
    api("org.slf4j:slf4j-api:1.7.30")

    testImplementation(kotlin("test"))
}

tasks {
    compileKotlin {
        targetCompatibility = "1.8"
        sourceCompatibility = "1.8"
    }

    test {
        useJUnitPlatform()
    }
}
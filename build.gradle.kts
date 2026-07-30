import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.3.0"
}

group = "me.udnek"
version = "1.0-SNAPSHOT"
var minecraftVersion = "26.2"
var javaVersion = 25


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(files("../CoreU/build/libs/CoreU-1.0-SNAPSHOT.jar"))
    compileOnly(files("../JeiU/build/libs/JeiU-1.0-SNAPSHOT.jar"))
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion.build.+")

    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
    }
    compileJava {
        options.release.set(javaVersion)
    }
    shadowJar {
        archiveBaseName.set("RpgU")
        archiveClassifier.set("")
        archiveVersion.set(version.toString())
    }
}

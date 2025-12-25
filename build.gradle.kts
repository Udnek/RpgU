import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.3.0"
}

group = "me.udnek"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    compileOnly(files("C:/Users/glebd/OneDrive/Documents/CODING/Java/CoreU/build/libs/CoreU-1.0-SNAPSHOT.jar"))
    compileOnly(files("C:\\Users\\glebd\\OneDrive\\Documents\\CODING\\Java\\JeiU\\out\\artifacts\\JeiU.jar"))
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
    }
    compileJava {
        options.release.set(21)
    }
    shadowJar {
        archiveBaseName.set("RpgU")
        archiveClassifier.set("")
        archiveVersion.set(version.toString())
    }
}

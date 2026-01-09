import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.3.0"
    //id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
}

group = "me.udnek"
version = "1.0-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {

    compileOnly(files("C:/Users/glebd/OneDrive/Documents/CODING/Java/CoreU/build/libs/CoreU-1.0-SNAPSHOT.jar"))
    compileOnly(files("C:\\Users\\glebd\\OneDrive\\Documents\\CODING\\Java\\JeiU\\build\\libs\\JeiU-1.0-SNAPSHOT.jar"))
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT") {
        exclude(group = "org.apache.commons", module = "commons-lang3")
    }
    compileOnly("org.apache.commons:commons-lang3:3.20.0")

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

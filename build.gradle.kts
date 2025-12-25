import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.3.0"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
}

group = "me.udnek"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    // Paper API
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    // Системные зависимости из pom.xml (CoreU и JeiU)
    compileOnly(files("H:/test/plugins/ItemsCoreU-1.0-SNAPSHOT-remapped.jar"))
    compileOnly(files("H:/test/plugins/JeiU.jar"))

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

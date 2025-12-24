plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
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
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

    // Системные зависимости из pom.xml (CoreU и JeiU)
    compileOnly(files("H:/test/plugins/ItemsCoreU-1.0-SNAPSHOT-remapped.jar"))
    compileOnly(files("H:/test/plugins/JeiU.jar"))
}

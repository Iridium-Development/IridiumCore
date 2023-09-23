plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.iridium"
version = "1.7.5"
description = "IridiumCore"

allprojects {
    apply(plugin = "java")

    java.sourceCompatibility = JavaVersion.VERSION_1_8

    repositories {
        mavenCentral()
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://repo.rosewooddev.io/repository/public/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    }

    dependencies {
        // Dependencies that we want to shade in
        implementation("com.github.cryptomorin:XSeries:9.4.0") { isTransitive = false }

        // Other dependencies that are not required or already available at runtime
        compileOnly("org.jetbrains:annotations:24.0.1")
        compileOnly("org.projectlombok:lombok:1.18.26")

        // Enable lombok annotation processing
        annotationProcessor("org.projectlombok:lombok:1.18.26")
    }
}

dependencies {
    // Shade all the subprojects into the jar
    subprojects.forEach { implementation(it) }
}

tasks {
    jar {
        dependsOn(shadowJar)
        enabled = false
    }

    shadowJar {
        archiveClassifier.set("")
        relocate("de.tr7zw.changeme.nbtapi", "com.iridium.iridiumcore.dependencies.nbtapi")
        relocate("com.iridium.iridiumcolorapi", "com.iridium.iridiumcore.dependencies.iridiumcolorapi")
        relocate("org.yaml.snakeyaml", "com.iridium.iridiumcore.dependencies.snakeyaml")
        relocate("io.papermc.lib", "com.iridium.iridiumcore.dependencies.paperlib")
        relocate("com.cryptomorin.xseries", "com.iridium.iridiumcore.dependencies.xseries")
        relocate("com.fasterxml.jackson", "com.iridium.iridiumcore.dependencies.fasterxml")
    }

    compileJava {
        options.encoding = "UTF-8"
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
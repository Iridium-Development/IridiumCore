plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.iridium"
version = "1.1.9"
description = "IridiumCore"

allprojects {
    apply(plugin = "java")

    java.sourceCompatibility = JavaVersion.VERSION_1_8

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://repo.mvdw-software.com/content/groups/public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://maven.sk89q.com/artifactory/repo")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven("https://ci.ender.zone/plugin/repository/everything/")
        maven("https://jitpack.io")
        maven("https://repo.rosewooddev.io/repository/public/")
        maven("https://nexus.savagelabs.net/repository/maven-releases/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://repo.jeff-media.de/maven2/")
    }

    dependencies {
        // Dependencies that we want to shade in
        implementation("org.jetbrains:annotations:16.0.1")
        implementation("com.github.cryptomorin:XSeries:8.1.0")

        // Other dependencies that are not required or already available at runtime
        compileOnly("org.projectlombok:lombok:1.18.20")

        // Enable lombok annotation processing
        annotationProcessor("org.projectlombok:lombok:1.18.20")
    }
}

dependencies {
    // Shade all the sub-projects into the jar
    subprojects.forEach { implementation(it) }
}

tasks {
    jar {
        dependsOn("shadowJar")
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

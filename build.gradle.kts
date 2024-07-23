plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.iridium"
version = "2.0.5"
description = "IridiumCore"

allprojects {
    apply(plugin = "java")

    java.sourceCompatibility = JavaVersion.VERSION_1_8

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://repo.rosewooddev.io/repository/public/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
        maven("https://libraries.minecraft.net")
    }

    dependencies {
        // Dependencies that we want to shade in
        implementation("com.github.cryptomorin:XSeries:11.0.0")

        // Other dependencies that are not required or already available at runtime
        compileOnly("org.jetbrains:annotations:24.1.0")
        compileOnly("org.projectlombok:lombok:1.18.34")
        // This is needed for XSkin, but isnt added to the XSeries jar, potentially a bug that will be fixed in a later release
        compileOnly("com.mojang:authlib:1.5.25")

        // Enable lombok annotation processing
        annotationProcessor("org.projectlombok:lombok:1.18.34")
    }
}

dependencies {
    // Shade all the subprojects into the jar
    subprojects.forEach { implementation(it) }
}

tasks {
    // Add the shadowJar task to the build task
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        fun relocate(origin: String) =
            relocate(origin, "com.iridium.iridiumcore.dependencies${origin.substring(origin.lastIndexOf('.'))}")

        relocate("de.tr7zw.changeme.nbtapi")
        relocate("com.iridium.iridiumcolorapi")
        relocate("org.yaml.snakeyaml")
        relocate("io.papermc")
        relocate("com.cryptomorin.xseries")
        relocate("com.fasterxml.jackson")
        relocate("org.apache.commons")

        archiveClassifier.set("")
    }

    compileJava {
        options.encoding = "UTF-8"
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        setGroupId("com.iridium")
        setArtifactId("IridiumCore")
        setVersion(version)
        artifact(tasks["shadowJar"])
    }
}

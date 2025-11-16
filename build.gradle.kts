plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.2.2"
}

group = "com.iridium"
version = "2.0.12"
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
        implementation("com.github.cryptomorin:XSeries:13.5.1")

        // Other dependencies that are not required or already available at runtime
        compileOnly("org.jetbrains:annotations:26.0.2-1")
        compileOnly("org.projectlombok:lombok:1.18.42")
        // This is needed for XSkin, but isnt added to the XSeries jar, potentially a bug that will be fixed in a later release
        compileOnly("com.mojang:authlib:1.5.25")

        // Enable lombok annotation processing
        annotationProcessor("org.projectlombok:lombok:1.18.42")
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
    }

    compileJava {
        options.encoding = "UTF-8"
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        groupId = "com.iridium"
        artifactId = "IridiumCore"
        version = version
        artifact(tasks["shadowJar"])
    }
}

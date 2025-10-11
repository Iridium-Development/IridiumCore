dependencies {
    // Include all the multiversion submodules
    val multiVersionProjects = project(":multiversion").dependencyProject.subprojects
    multiVersionProjects.forEach { implementation(it) }

    // Dependencies that we want to shade in
    implementation("de.tr7zw:item-nbt-api:2.15.3")
    implementation("com.iridium:IridiumColorAPI:1.0.9")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.20.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.20")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.20.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.20.0")
    implementation("org.yaml:snakeyaml:2.5")
    implementation("io.papermc:paperlib:1.0.8")
    implementation("org.apache.commons:commons-lang3:3.18.0")

    // Other dependencies that are not required or already available at runtime
    compileOnly("org.jetbrains:annotations:26.0.2-1")
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
}

tasks {
    build {
        dependsOn(processResources)
    }
}

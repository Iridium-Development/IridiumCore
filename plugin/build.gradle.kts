dependencies {
    // Include all the multiversion submodules
    val multiVersionProjects = project(":multiversion").dependencyProject.subprojects
    multiVersionProjects.forEach { implementation(it) }

    // Dependencies that we want to shade in
    implementation("de.tr7zw:item-nbt-api:2.12.2")
    implementation("com.iridium:IridiumColorAPI:1.0.9")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.3")
    implementation("org.yaml:snakeyaml:2.2")
    implementation("io.papermc:paperlib:1.0.8")

    // Other dependencies that are not required or already available at runtime
    compileOnly("org.jetbrains:annotations:24.1.0")
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
}

tasks {
    build {
        dependsOn(processResources)
    }
}

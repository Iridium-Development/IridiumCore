# IridiumCore
![GitHub](https://img.shields.io/github/license/Iridium-Development/IridiumCore?color=479fc0)

## Introduction

IridiumCore is a library for Iridium Development's Spigot plugins.

It houses NMS and version specific code, as well as gives GUIs, Items, and other plugin aspects a central location for ease of use.

### Compiling

Clone the repo, and run the [build.gradle.kts](https://github.com/Iridium-Development/IridiumCore/blob/master/build.gradle.kts) script.

If you need to take a look, here is the [Nexus](https://nexus.iridiumdevelopment.net/#browse/browse:maven-public:com%2Firidium%2FIridiumCore) repo.

### Development

#### Maven

```xml
<repository>
  <id>IridiumDevelopment</id>
  <url>https://nexus.iridiumdevelopment.net/repository/maven-releases/</url>
</repository>
```
```xml
<dependency>
  <groupId>com.iridium</groupId>
  <artifactId>IridiumCore</artifactId>
  <version>2.0.8.8</version>
  <scope>provided</scope>
</dependency>
```

#### Kotlin DSL (Gradle)

```kts
repositories {
  maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
}
```
```kts
dependencies {
  implementation("com.iridium:IridiumCore:2.0.8.8")
}
```

## Support

If you think you've found a bug, please make sure you isolate the issue down to IridiumCore before posting an issue in our [Issues](https://github.com/Iridium-Development/IridiumCore/issues) tab. While you're there, please follow our issues guidelines.

If you encounter any issues while using IridiumCore, feel free to join our support [Discord](https://discord.gg/6HJ73mWE7P).

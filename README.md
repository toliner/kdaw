<h1 align="center">Diskord</h1>

<p align="center"><b>Diskord</b>  is a lightweight <a href="https://discord.com/developers/docs/intro">Discord API</a> wrapper written in Kotlin. It can run even large scale bots stably.</p>

<div align="center">
    <a href="https://github.com/Kotlin-chan/diskord"><img src="https://img.shields.io/github/workflow/status/Kotlin-chan/diskord/build?style=flat-square" alt="Build Result"></a>
    <a href="https://jitpack.io/#Kotlin-chan/diskord"><img src="https://img.shields.io/jitpack/v/github/Kotlin-chan/diskord?label=Version&style=flat-square&color=blueviolet" alt="jitpack release version"></a>
    <a href="https://opensource.org/licenses/mit-license.php"><img src="https://img.shields.io/static/v1?label=License&message=Mit&style=flat-square&color=blue" alt="License"></a>
    <a href="https://twitter.com/kotx__"><img src="https://img.shields.io/static/v1?label=Developer&message=Kotx__&style=flat-square&color=orange" alt="developer"></a>
</div>

⚠️**This library is currently under development (beta version is `0.*.*`), and the API will be changed or removed
without notice.**

## ⚡ Quickstart

![](https://i.imgur.com/EXAMPLE_GIF.gif)

```kotlin
fun main() {
    Diskord.create("<< insert bot token here >>") {
        listen<ServerMessageCreateEvent>(priority = 1) {
            println("[Message] ${it.server} | ${it.channel} | ${it.user} | ${it.text}")
        }

        listen(BasicListener)
    }
}

object BasicListener {
    @EventTarget
    fun ready(event: ReadyEvent) {
        println("Hello~~!")
    }
    
    @EventTarget(priority = 5)
    suspend fun message(event: ServerMessageCreateEvent) {
        event.reply("hi!")
    } 
}
```

## ⚙️ Installation

[![](https://img.shields.io/jitpack/v/github/Kotlin-Chan/diskord?label=Version&style=flat-square&color=blueviolet)](https://jitpack.io/Kotlin-Chan/diskord)

Replace `<version>` with the version you want to use of jitpack.

<details>
<summary>Gradle Kotlin DSL</summary>
<div>

```kotlin
repositories {
    maven("https://jitpack.io")
}
```

```kotlin
dependencies {
    implementation("com.github.Kotlin-Chan:diskord:<version>")
}
```

</div>
</details>

<details>
<summary>Gradle</summary>
<div>

```groovy
repositories {
    maven { url "https://jitpack.io" }
}
```

```groovy
dependencies {
    implementation "com.github.Kotlin-Chan:diskord:<version>"
}
```

</div>
</details>

<details>
<summary>Maven</summary>
<div>

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml

<dependency>
    <groupId>com.github.Kotlin-Chan</groupId>
    <artifactId>diskord</artifactId>
    <version>version</version>
</dependency>
```

</div>
</details>

## 📝 Dependencies

- [kotlin](https://github.com/JetBrains/kotlin)

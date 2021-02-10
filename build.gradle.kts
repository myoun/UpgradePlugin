import kr.entree.spigradle.kotlin.*

plugins {
    kotlin("jvm") version "1.4.30"
    id("kr.entree.spigradle") version "2.2.3"
}

group = "kr.myoung2"
version = "1.0"

repositories {
    mavenCentral()
}

spigot {
    authors = listOf("명이")
    apiVersion = "1.16"
    commands{
        commands.create("upgrade"){
            usage = "/upgrade impstone"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly(paper("1.16.4"))
}

val shade = configurations.create("shade")
shade.extendsFrom(configurations.implementation.get())

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    jar {
        from(
            shade.map {
                if (it.isDirectory)
                    it
                else
                    zipTree(it)
            }
        )
    }
}
plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "dev.fastmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

publishing {
    repositories {
        maven {
            name = "temp"
            val tempMavenPath = System.getenv("mvn_temp") ?: "${project.buildDir}/tmp/mvn_temp"
            val uri = File(tempMavenPath).toURI()
            println("mvn_temp: $uri")
            url = uri
        }
    }
}

afterEvaluate {
    if (System.getenv("CI") == "true") {
        println("Writing version info")
        val dir = File(buildDir, "tmp/maven-repo")
        dir.mkdirs()
        File(dir, "version_info.txt").writeText("${rootProject.group}:${rootProject.name}:${rootProject.version}")
    }
}
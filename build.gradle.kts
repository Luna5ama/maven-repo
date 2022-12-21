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
            url = uri
        }
    }
}

afterEvaluate {
    if (System.getenv("CI") == "true") {
        val dir = File(rootProject.projectDir,"build/tmp/maven-repo")
        dir.mkdirs()
        val file = File(dir, "version_info.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.writeText("${rootProject.group}:${rootProject.name}:${rootProject.version}")
    }
}
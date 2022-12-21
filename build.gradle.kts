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
            val tempMavenPath = System.getenv("mvn_temp") ?: "${project.buildDir}/tmp/maven-repo"
            url = uri("file://$tempMavenPath")
        }
    }
}

afterEvaluate {
    if (System.getenv("CI") == "true") {
        File(buildDir, "version_info.txt").writeText("${rootProject.group}:${rootProject.name}:${rootProject.version}")
    }
}
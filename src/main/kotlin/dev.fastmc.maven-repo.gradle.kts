plugins {
    `maven-publish`
}

configure<PublishingExtension> {
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
        val dir = File(projectDir,"build/tmp/maven-repo")
        dir.mkdirs()
        val file = File(dir, "version_info.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.writeText("${rootProject.group}:${rootProject.name}:${rootProject.version}")
    }
}
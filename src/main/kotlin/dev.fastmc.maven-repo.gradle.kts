plugins {
    `maven-publish`
}

configure<PublishingExtension> {
    repositories {
        maven {
            name = "temp"
            val tempMavenPath = System.getenv("mvn_temp") ?: "${project.buildDir}/tmp/mvn_temp"
            url = File(tempMavenPath).toURI()
        }
    }
}

afterEvaluate {
    if (System.getenv("CI") == "true") {
        val dir = File(buildDir, "tmp/maven-repo")
        dir.mkdirs()
        File(dir, "version_info.txt").writeText("${rootProject.group}:${rootProject.name}:${rootProject.version}")
    }
}
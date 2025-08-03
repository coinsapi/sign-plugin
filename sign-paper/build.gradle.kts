plugins {
    alias(libs.plugins.minotaur)
}

dependencies {
    api(project(":sign-shared")) {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")
    }
    compileOnly(rootProject.libs.paper.api)
    implementation(rootProject.libs.bundles.cloud.paper) {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")
    }
    compileOnly(rootProject.libs.bundles.coroutine)
    compileOnly(rootProject.libs.simplecloud.controller.api)
}

tasks {
    shadowJar {
        exclude("kotlin")
        exclude("kotlinx")
        mergeServiceFiles()
    }
}

modrinth {
    token.set(project.findProperty("modrinthToken") as String? ?: System.getenv("MODRINTH_TOKEN"))
    projectId.set("M2XJERK4")
    versionNumber.set(rootProject.version.toString())
    versionType.set("beta")
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll(
        "1.20",
        "1.20.1",
        "1.20.2",
        "1.20.3",
        "1.20.4",
        "1.20.5",
        "1.20.6",
        "1.21",
        "1.21.1",
        "1.21.2",
        "1.21.3",
        "1.21.4",
        "1.21.5",
        "1.21.6",
        "1.21.7",
        "1.21.8"
    )
    loaders.add("paper")
    changelog.set("https://docs.simplecloud.app/changelog")
    syncBodyFrom.set(rootProject.file("README.md").readText())
}

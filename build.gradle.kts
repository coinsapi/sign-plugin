import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
}

val baseVersion = "0.0.1"
val commitHash = System.getenv("COMMIT_HASH")
val snapshotversion = "${baseVersion}-dev.$commitHash"

allprojects {
    group = "app.simplecloud.plugin"
    version = if (commitHash != null) snapshotversion else baseVersion

    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven("https://buf.build/gen/maven")
        maven("https://repo.simplecloud.app/snapshots")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.gradleup.shadow")

    dependencies {
        testImplementation(rootProject.libs.kotlin.test)
        compileOnly(rootProject.libs.kotlin.jvm)
    }

    kotlin {
        jvmToolchain(21)
    }

    tasks {
        withType<KotlinCompile> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }

        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        named("shadowJar", ShadowJar::class) {
            mergeServiceFiles()
            archiveFileName.set("${project.name}.jar")
        }

        processResources {
            expand(
                "version" to project.version,
                "name" to project.name
            )
        }
    }
}

tasks.processResources {
    expand(
        "version" to project.version,
        "name" to project.name
    )
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
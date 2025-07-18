// Pull COMPOSE_SNAPSHOT_ID from the environment (if set)
val snapshotVersion: String? = System.getenv("COMPOSE_SNAPSHOT_ID")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // from(files("gradle/libs.versions.toml"))
        }
    }

    repositories {
        snapshotVersion?.let {
            println("Using Compose snapshot repo: https://androidx.dev/snapshots/builds/$it/artifacts/repository/")
            maven { url = uri("https://androidx.dev/snapshots/builds/$it/artifacts/repository/") }
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "LMPlayground"
include(":app")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }


}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "plugin"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

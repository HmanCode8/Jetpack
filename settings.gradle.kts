pluginManagement {
    repositories {
        // 每个仓库单独写！不能写在同一个 maven 里
        maven { url = uri("https://esri.jfrog.io/artifactory/arcgis") }
        maven { url = uri("https://jitpack.io") }
        maven { url =uri("https://oss.sonatype.org/content/repositories/snapshots/") }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 这里也必须分开写！
        maven { url = uri("https://esri.jfrog.io/artifactory/arcgis") }
        maven { url = uri("https://jitpack.io") }
        maven { url =uri("https://oss.sonatype.org/content/repositories/snapshots/") }

        google()
        mavenCentral()
    }
}

rootProject.name = "JetCompose"
include(":app")
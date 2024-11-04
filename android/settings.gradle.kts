pluginManagement {
    repositories {
//        maven("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
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

    includeBuild("../node_modules/@react-native/gradle-plugin")
}

plugins {
    id("com.facebook.react.settings")
}

extensions.configure<com.facebook.react.ReactSettingsExtension> { autolinkLibrariesFromCommand() }

//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
////        maven("https://maven.aliyun.com/nexus/content/groups/public/")
////        maven("https://maven.aliyun.com/nexus/content/repositories/jcenter")
////        maven("https://maven.aliyun.com/nexus/content/repositories/google")
////        maven("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
//        google()
//        mavenCentral()
//        maven("https://jitpack.io")
//    }
//}

rootProject.name = "ComposeDemos"

include(":app")
includeBuild("../node_modules/@react-native/gradle-plugin")

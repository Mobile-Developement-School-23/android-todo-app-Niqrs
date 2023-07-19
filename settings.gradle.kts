pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ToDo App"

include(":app")

include(":auth:ui")
include(":auth:domain")
include(":auth:data")

include(":tasks:ui")
include(":tasks:data")
include(":tasks:domain")

include(":edit:ui")

include(":core:ui")
include(":core:data")
include(":core:di")

include(":settings:domain")
include(":settings:data")

include(":other:work")
include(":other:alarm")

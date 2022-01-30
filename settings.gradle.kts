pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Component Library"
include(":app")
include(":puzzle")

// Enable Version Catalogs
enableFeaturePreview("VERSION_CATALOGS")

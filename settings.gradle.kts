pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Component Library"
include(":app")

// Enable Version Catalogs
enableFeaturePreview("VERSION_CATALOGS")

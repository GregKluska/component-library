import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.gregkluska.compose.buildsrc.DependencyUpdates
import com.gregkluska.compose.buildsrc.ReleaseType

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs") as org.gradle.accessors.dm.LibrariesForLibs

        classpath(libs.android.pluginGradle)
        classpath(libs.kotlin.pluginGradle)
        classpath(libs.benmanes.gvp)
    }
}

apply(plugin = "com.github.ben-manes.versions")

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.withType<DependencyUpdatesTask> {

    rejectVersionIf {

        val current = DependencyUpdates.versionToRelease(currentVersion)
        // If we're using a SNAPSHOT, ignore since we must be doing so for a reason.
        if (current == ReleaseType.SNAPSHOT) reject("Using SNAPSHOT")

        // Otherwise we reject if the candidate is more 'unstable' than our version
        val candidateVersion = DependencyUpdates.versionToRelease(candidate.version)
        candidateVersion.isLessStableThan(current)
    }

    // optional parameters
    checkForGradleUpdate = true
    outputFormatter = "html"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
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
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.41.0")
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
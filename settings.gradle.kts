plugins {
    id("io.cloudflight.autoconfigure-settings") version "0.8.3"
}

configure<org.ajoberstar.reckon.gradle.ReckonExtension>() {
    setDefaultInferredScope("patch")
}

rootProject.name = "json-wrapper"
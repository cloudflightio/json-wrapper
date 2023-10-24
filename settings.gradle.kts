plugins {
    id("io.cloudflight.autoconfigure-settings") version "1.0.2"
}

rootProject.name = "json-wrapper"

configure<org.ajoberstar.reckon.gradle.ReckonExtension> {
    setScopeCalc(calcScopeFromCommitMessages())
}

rootProject.name = "json-wrapper"

plugins {
    id("org.ajoberstar.reckon.settings") version "0.17.0-beta.4"
}

configure<org.ajoberstar.reckon.gradle.ReckonExtension> {
    snapshots()

    setScopeCalc(calcScopeFromProp().or(calcScopeFromCommitMessages()))
    setStageCalc(calcStageFromProp())
}
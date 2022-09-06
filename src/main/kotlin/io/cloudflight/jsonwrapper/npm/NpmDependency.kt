package io.cloudflight.jsonwrapper.npm

@kotlinx.serialization.Serializable
class NpmDependency(
    val version: String,
    val dev: Boolean = false,
    val optional: Boolean = false,
    val requires: Map<String, String> = emptyMap(),
    val dependencies: Map<String, NpmDependency> = emptyMap()
)
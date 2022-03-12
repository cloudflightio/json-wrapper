package io.cloudflight.jsonwrapper.npm

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class NpmDependency(
    val version: String,
    val dev: Boolean = false,
    val requires: Map<String, String> = emptyMap(),
    val dependencies: Map<String, NpmDependency> = emptyMap()
)
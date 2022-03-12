package io.cloudflight.jsonwrapper.npm

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class NpmDependency @JsonCreator constructor(
    @JsonProperty("version") val version: String,
    @JsonProperty("dev") val dev: Boolean = false,
    @JsonProperty("requires") val requires: Map<String, String>?,
    @JsonProperty("dependencies") val dependencies: Map<String, NpmDependency>?
)
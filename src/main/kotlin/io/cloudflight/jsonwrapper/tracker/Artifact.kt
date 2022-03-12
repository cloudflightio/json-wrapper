package io.cloudflight.jsonwrapper.tracker

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
data class Artifact @JsonCreator constructor(
    @JsonProperty("artifact") val artifact: String,
    @JsonProperty("classifier") val classifier: String? = null,
    @JsonProperty("type") val type: String? = null,
    @JsonProperty("trail") val trail: List<String> = emptyList()
)
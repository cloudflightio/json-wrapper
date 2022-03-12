package io.cloudflight.jsonwrapper.tracker

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties
data class Artifact(
    var artifact: String? = null,
    var classifier: String? = null,
    var type: String? = null,
    var trail: List<String> = emptyList()
)
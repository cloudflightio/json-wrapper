package io.cloudflight.jsonwrapper.tracker

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
data class Project @JsonCreator constructor(
    @JsonProperty("artifact") val artifact: String?,
    @JsonProperty("packaging") val packaging: String?,
    @JsonProperty("url") val url: String?
)
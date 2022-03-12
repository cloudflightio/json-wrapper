package io.cloudflight.jsonwrapper.tracker

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties
data class Project(
    val artifact: String?,
    val packaging: String?,
    val url: String?
)
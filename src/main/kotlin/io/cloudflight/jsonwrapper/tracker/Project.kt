package io.cloudflight.jsonwrapper.tracker

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val artifact: String,
    val packaging: String,
    val url: String?
)
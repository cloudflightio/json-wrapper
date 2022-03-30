package io.cloudflight.jsonwrapper.tracker

import kotlinx.serialization.Serializable

@Serializable
data class Artifact(
    val artifact: String,
    val classifier: String?,
    val type: String,
    val trail: List<String>?
)
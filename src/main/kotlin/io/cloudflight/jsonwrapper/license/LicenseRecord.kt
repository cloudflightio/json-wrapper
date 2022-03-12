package io.cloudflight.jsonwrapper.license

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.cloudflight.jsonwrapper.Parser
import java.io.File

data class LicenseRecord @JsonCreator constructor(
    @JsonProperty("project") val project: String,
    @JsonProperty("description") val description: String?,
    @JsonProperty("version") val version: String,
    @JsonProperty("developers") val developers: List<String>,
    @JsonProperty("url") val url: String?,
    @JsonProperty("year") val year: String?,
    @JsonProperty("licenses") val licenses: List<LicenseEntry>,
    @JsonProperty("dependency") val dependency: String
) {
    companion object {
        fun readFromFile(file: File): LicenseRecord {
            return Parser.parseFile(file, LicenseRecord::class.java, withKotlinSupport = false)
        }
    }
}

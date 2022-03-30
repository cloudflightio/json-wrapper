package io.cloudflight.jsonwrapper.license

import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.io.InputStream

@kotlinx.serialization.Serializable
data class LicenseEntry(
    val license: String,
    @SerialName("license_id") val licenseId: String? = null,
    @SerialName("license_url") val licenseUrl: String
) {
    companion object {

        private val json = Json {
            ignoreUnknownKeys = true
        }

        fun readFromFile(file: File): LicenseEntry {
            return json.decodeFromStream(file.inputStream())
        }

        fun readFromStream(inputStream: InputStream): List<LicenseEntry> {
            return json.decodeFromStream(inputStream)
        }
    }
}

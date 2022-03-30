package io.cloudflight.jsonwrapper.license

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.io.InputStream

@kotlinx.serialization.Serializable
data class LicenseRecord(
    val project: String,
    val description: String?,
    val version: String,
    val developers: List<String>,
    val url: String?,
    val year: String?,
    val licenses: List<LicenseEntry>,
    val dependency: String
) {
    companion object {

        private val json = Json {
            ignoreUnknownKeys = true
        }

        fun readFromFile(file: File): LicenseRecord {
            return json.decodeFromStream(file.inputStream())
        }

        fun readFromStream(inputStream: InputStream): List<LicenseRecord> {
            return json.decodeFromStream(inputStream)
        }
    }
}

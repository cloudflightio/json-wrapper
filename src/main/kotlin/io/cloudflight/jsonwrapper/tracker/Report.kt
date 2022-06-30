package io.cloudflight.jsonwrapper.tracker

import io.cloudflight.jsonwrapper.cleancode.CleanCodeReport
import io.cloudflight.jsonwrapper.license.LicenseRecord
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

@Serializable
class Report {
    lateinit var buildTool: BuildTool
    lateinit var buildToolVersion: String
    var pluginVersion: String? = null

    /**
     * The parent pom.xml, only use this in maven builds, leave empty for gradle!!
     */
    var parent: Project? = null
    lateinit var project: Project
    var compile: List<Artifact> = emptyList()
    var provided: List<Artifact> = emptyList()
    var runtime: List<Artifact> = emptyList()
    var test: List<Artifact> = emptyList()
    var development: List<Artifact> = emptyList()
    var cleanCodeReport: CleanCodeReport? = null
    var licenseRecords: List<LicenseRecord> = emptyList()

    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
        }

        fun readFromFile(file: File): Report {
            return file.inputStream().use {
                json.decodeFromStream(it)
            }
        }

        fun readFromJson(jsonString: String): Report {
            return json.decodeFromString(serializer(), jsonString)
        }
    }
}
package io.cloudflight.jsonwrapper.tracker

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.cloudflight.jsonwrapper.Parser
import io.cloudflight.jsonwrapper.cleancode.CleanCodeReport
import io.cloudflight.jsonwrapper.license.LicenseRecord
import java.io.File

@JsonIgnoreProperties
class Report {
    var buildTool: BuildTool? = null
    var buildToolVersion: String? = null
    var pluginVersion: String? = null

    /**
     * The parent pom.xml, only use this in maven builds, leave empty for gradle!!
     */
    var parent: Project? = null
    var project: Project? = null
    var compile: List<Artifact> = emptyList()
    var provided: List<Artifact> = emptyList()
    var runtime: List<Artifact> = emptyList()
    var test: List<Artifact> = emptyList()
    var development: List<Artifact> = emptyList()
    var cleanCodeReport: CleanCodeReport? = null
    val licenseRecords: List<LicenseRecord> = emptyList()

    companion object {
        fun readFromFile(file: File): Report {
            return Parser.parseFile(file, Report::class.java, withKotlinSupport = true)
        }
    }
}
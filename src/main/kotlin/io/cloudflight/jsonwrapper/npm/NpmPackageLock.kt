package io.cloudflight.jsonwrapper.npm

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.cloudflight.jsonwrapper.Parser
import java.io.File

/**
 * A model of a `package-lock.json` file as described here: https://docs.npmjs.com/cli/v8/configuring-npm/package-lock-json
 *
 * @author Klaus Lehner
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class NpmPackageLock @JsonCreator constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("version") val version: String,
    @JsonProperty("dependencies") val dependencies: Map<String, NpmDependency> = emptyMap()
) {
    companion object {
        fun readFromFile(file: File): NpmPackageLock {
            return Parser.parseFile(file, NpmPackageLock::class.java)
        }
    }
}
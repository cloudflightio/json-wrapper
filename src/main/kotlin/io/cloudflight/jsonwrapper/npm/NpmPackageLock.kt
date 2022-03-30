package io.cloudflight.jsonwrapper.npm

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

/**
 * A model of a `package-lock.json` file as described here: https://docs.npmjs.com/cli/v8/configuring-npm/package-lock-json
 *
 * @author Klaus Lehner
 */
@kotlinx.serialization.Serializable
class NpmPackageLock(
    val name: String,
    val version: String,
    val dependencies: Map<String, NpmDependency> = emptyMap()
) {
    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
        }

        fun readFromFile(file: File): NpmPackageLock {
            return json.decodeFromStream(file.inputStream())
        }
    }
}
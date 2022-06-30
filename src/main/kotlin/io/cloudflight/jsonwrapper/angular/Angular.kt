package io.cloudflight.jsonwrapper.angular

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

/**
 * A simplified model of an angular.json file as described here: https://angular.io/guide/workspace-config
 */
@Serializable
class Angular(
    val projects: Map<String, Project> = emptyMap()
) {

    fun getSourceRootsOfAllProjects(): Set<String> {
        return projects.values.mapNotNull { it.sourceRoot }.filter { it.isNotEmpty() }.toSet()
    }

    @Serializable
    class Project(
        val sourceRoot: String?
    )

    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
        }

        fun readFromFile(file: File): Angular {
            return file.inputStream().use {
                json.decodeFromStream(it)
            }
        }
    }
}

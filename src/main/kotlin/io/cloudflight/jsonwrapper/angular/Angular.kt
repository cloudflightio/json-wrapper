package io.cloudflight.jsonwrapper.angular

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.cloudflight.jsonwrapper.Parser
import java.io.File

@JsonIgnoreProperties(ignoreUnknown = true)
class Angular constructor(
    val projects: Map<String, Project> = emptyMap()
) {

    @JsonIgnore
    fun getSourceRootsOfAllProjects(): Set<String> {
        return projects.values.mapNotNull { it.sourceRoot }.filter { it.isNotEmpty() }.toSet()
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Project @JsonCreator constructor(
        @JsonProperty("sourceRoot") val sourceRoot: String?
    )

    companion object {
        fun readFromFile(file: File): Angular {
            return Parser.parseFile(file, Angular::class.java)
        }
    }
}

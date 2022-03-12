package io.cloudflight.jsonwrapper.npm

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.cloudflight.jsonwrapper.Parser
import java.io.File

/**
 * model of a package.json file
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class NpmPackage {
    lateinit var name: String
    lateinit var version: String
    var private: Boolean = false
    var license: String? = null
    var homepage: String? = null
    var description: String? = null
    var author: Person? = null
    var contributors: List<Person> = emptyList()
    var scripts: Map<String, String> = emptyMap()
    var dependencies: Map<String, String> = emptyMap()
    var devDependencies: Map<String, String> = emptyMap()

    companion object {
        fun readFromFile(file: File): NpmPackage {
            return Parser.parseFile(file, NpmPackage::class.java)
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Person @JsonCreator constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("url") val url: String?,
    @JsonProperty("email") val email: String?
) {

    @JsonCreator
    constructor(name: String) : this(name, null, null)
}
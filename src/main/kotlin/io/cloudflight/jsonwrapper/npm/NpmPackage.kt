package io.cloudflight.jsonwrapper.npm

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import java.io.File

/**
 * A model of a package.json file
 */
@Serializable
class NpmPackage {
    lateinit var name: String
    lateinit var version: String
    var private: Boolean = false
    var license: String? = null
    var homepage: String? = null
    var description: String? = null
    @Serializable(with = PersonByStringSerializer::class)
    var author: Person? = null
    var contributors: List<@Serializable(with = PersonByStringSerializer::class) Person> = emptyList()
    var scripts: Map<String, String> = emptyMap()
    var dependencies: Map<String, String> = emptyMap()
    var devDependencies: Map<String, String> = emptyMap()

    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
        }

        fun readFromFile(file: File): NpmPackage {
            return json.decodeFromStream(file.inputStream())
        }
    }
}

private object PersonByStringSerializer : JsonTransformingSerializer<Person>(Person.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element is JsonPrimitive) JsonObject(mapOf("name" to element)) else element
}

@Serializable
class Person(
    val name: String,
    val url: String? = null,
    val email: String? = null
)

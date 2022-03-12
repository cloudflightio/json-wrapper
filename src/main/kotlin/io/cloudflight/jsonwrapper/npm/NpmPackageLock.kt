package io.cloudflight.jsonwrapper.npm

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * model of a package.json file
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class NpmPackageLock {
    lateinit var name: String
    lateinit var version: String
    var dependencies: Map<String, NpmDependency> = emptyMap()
}
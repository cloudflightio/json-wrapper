package io.cloudflight.jsonwrapper.npm

object NpmUtils {

    /**
     * @return GAV maven coordinates (group - artifact - version) for the given [NpmDependency]
     */
    fun getGavForNpmEntry(entry: Map.Entry<String, NpmDependency>): String {
        val group: String
        val artifact: String
        val split = entry.key.split("/").toTypedArray()
        if (split.size == 1) {
            group = "@npm"
            artifact = split[0]
        } else if (split.size == 2) {
            group = split[0]
            artifact = split[1]
        } else {
            throw IllegalArgumentException(entry.key + " has more than one / in its path")
        }
        return group + ":" + artifact + ":" + entry.value.version
    }
}
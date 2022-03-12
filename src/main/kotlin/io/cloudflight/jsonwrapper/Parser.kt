package io.cloudflight.jsonwrapper

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.IOException

internal object Parser {

    internal val objectMapper = ObjectMapper()

    @Throws(IOException::class)
    fun <T> parseFile(file: File, clazz: Class<T>): T {
        if (!file.exists()) {
            throw IOException("$file does not exist")
        }
        try {
            return objectMapper.readValue(file, clazz)
        } catch (ex: Exception) {
            throw IOException("Error at parsing $file", ex)
        }
    }
}
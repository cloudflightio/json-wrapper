package io.cloudflight.jsonwrapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.File
import java.io.IOException

internal object Parser {

    internal val objectMapper = ObjectMapper()
    internal val objectMapperWithKotlinSupport = ObjectMapper().also { it.registerModule(KotlinModule()) }

    @Throws(IOException::class)
    fun <T> parseFile(file: File, clazz: Class<T>, withKotlinSupport:Boolean=true): T {
        if (!file.exists()) {
            throw IOException("$file does not exist")
        }
        try {
            return if (withKotlinSupport) {
                objectMapperWithKotlinSupport.readValue(file, clazz)
            } else {
                objectMapper.readValue(file, clazz)
            }
        } catch (ex: Exception) {
            throw IOException("Error at parsing $file", ex)
        }
    }
}
package io.cloudflight.jsonwrapper.npm

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException

class NpmTest {

    @Test
    fun parsePackageJson() {
        val npmPackage: NpmPackage = NpmPackage.readFromFile(File("src/test/resources/npm/package.json"))
        assertEquals("q", npmPackage.name)
        assertEquals("MIT", npmPackage.license)
        Assertions.assertNotNull(npmPackage.author)
        assertEquals("Kris Kowal", npmPackage.author?.name)
        assertEquals(3, npmPackage.contributors.size)
    }

    @Test
    @Throws(IOException::class)
    fun parsePackageJsonWithSingleLineAuthor() {
        val npmPackage: NpmPackage = NpmPackage.readFromFile(File("src/test/resources/npm/package-author.json"))
        Assertions.assertNotNull(npmPackage.author)
        assertEquals("Catalysts", npmPackage.author?.name)
        assertTrue(npmPackage.private)
    }
}
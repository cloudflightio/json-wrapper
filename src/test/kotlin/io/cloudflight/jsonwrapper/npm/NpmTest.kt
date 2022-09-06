package io.cloudflight.jsonwrapper.npm

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

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
    fun parsePackageJsonWithSingleLineAuthor() {
        val npmPackage: NpmPackage = NpmPackage.readFromFile(File("src/test/resources/npm/package-author.json"))
        Assertions.assertNotNull(npmPackage.author)
        assertEquals("Catalysts", npmPackage.author?.name)
        assertTrue(npmPackage.private)
    }

    @Test
    fun parsePackageJsonBootstrap() {
        val npmPackage: NpmPackage = NpmPackage.readFromFile(File("src/test/resources/npm/package-bootstrap.json"))
        Assertions.assertNotNull(npmPackage.author)
        assertEquals("Twitter, Inc.", npmPackage.contributors.get(0).name)
    }

    @Test
    fun parsePackageLockJson() {
        val npmPackage: NpmPackageLock = NpmPackageLock.readFromFile(File("src/test/resources/npm/package-lock.json"))
        assertEquals("core-ui", npmPackage.name)
        assertEquals(1133, npmPackage.dependencies.size)
    }

    @Test
    fun parsePackageLockJsonWithoutDependencies() {
        val npmPackage: NpmPackageLock =
            NpmPackageLock.readFromFile(File("src/test/resources/npm/empty-package-lock.json"))
        assertEquals("npmbuild", npmPackage.name)
        assertEquals(0, npmPackage.dependencies.size)
    }

    @Test
    fun parsePackageLockJsonWithOptionalDependencies() {
        val npmPackage: NpmPackageLock =
            NpmPackageLock.readFromFile(File("src/test/resources/npm/package-lock-optional.json"))
        assertEquals("tracker", npmPackage.name)
        val fsevents = npmPackage.dependencies["fsevents"]
        assertNotNull(fsevents)
        assertTrue(fsevents!!.optional)

        val functionBind = npmPackage.dependencies["function-bind"]
        assertNotNull(functionBind)
        assertFalse(functionBind!!.optional)

    }
}
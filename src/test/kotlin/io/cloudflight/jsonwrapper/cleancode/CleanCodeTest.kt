package io.cloudflight.jsonwrapper.cleancode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class CleanCodeTest {

    @Test
    fun parsePackageJson() {
        val report: CleanCodeReport =
            CleanCodeReport.readFromFile(File("src/test/resources/cleancode/cleancode-report.json"))
        assertEquals("cleancode-commons", report.moduleName)
    }

}
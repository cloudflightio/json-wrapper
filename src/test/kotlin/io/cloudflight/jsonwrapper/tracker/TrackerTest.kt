package io.cloudflight.jsonwrapper.tracker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class TrackerTest {

    @Test
    fun parseDependencies() {
        val report: Report = Report.readFromFile(File("src/test/resources/tracker/dependencies.json"))
        assertEquals("6.7.1", report.buildToolVersion)
        assertEquals(2, report.development.size)
        assertEquals("jar", report.project.packaging)
    }

    @Test
    fun parseDependenciesWithoutPluginVersion() {
        val report: Report = Report.readFromFile(File("src/test/resources/tracker/report-1.json"))
        assertEquals("3.3.9", report.buildToolVersion)
    }

}
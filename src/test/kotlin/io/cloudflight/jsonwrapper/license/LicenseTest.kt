package io.cloudflight.jsonwrapper.license

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.io.File

class LicenseTest {

    @Test
    fun parseLicenseRecord() {
        val licenseRecord: LicenseRecord =
            LicenseRecord.readFromFile(File("src/test/resources/license/license-entry.json"))
        assertEquals(7, licenseRecord.developers.size)
    }

    @Test
    fun parseLicenseRecordWithoutId() {
        val licenseRecord: LicenseRecord =
            LicenseRecord.readFromFile(File("src/test/resources/license/license-entry-no-id.json"))
        assertEquals(1, licenseRecord.licenses.size)
        assertNull(licenseRecord.licenses[0].licenseId)
    }

    @Test
    fun equalsHashCode() {
        val record1 = LicenseRecord(
            project = "cloudflight-platform",
            description = "Cloudflight Platform",
            version = "1.0",
            developers = listOf("Cloudflight"),
            url = "https://cloudflight.io",
            year = "2020",
            licenses = listOf(LicenseEntry("CLF License", null, "https://cloudflight.io")),
            dependency = "io.cloudflight.platform:platform-bom:1.0"
        )

        val record2 = LicenseRecord(
            project = "cloudflight-platform",
            description = "Cloudflight Platform",
            version = "1.0",
            developers = listOf("Cloudflight"),
            url = "https://cloudflight.io",
            year = "2020",
            licenses = listOf(LicenseEntry("CLF License", null, "https://cloudflight.io")),
            dependency = "io.cloudflight.platform:platform-bom:1.0"
        )

        assertEquals(
            record1,
            record2,
            "equals/hashcode must be implemented properly as we are using HashSets later to ensure that licenses are only printed once to the reports"
        )
    }

    @Test
    fun serializeAndDeserialize() {
        val record1 = LicenseRecord(
            project = "cloudflight-platform",
            description = "Cloudflight Platform",
            version = "1.0",
            developers = listOf("Cloudflight"),
            url = "https://cloudflight.io",
            year = "2020",
            licenses = listOf(LicenseEntry("CLF License", "CLF", "https://cloudflight.io")),
            dependency = "io.cloudflight.platform:platform-bom:1.0"
        )


        val json = Json.encodeToString(LicenseRecord.serializer(), record1)
        val record2 = Json.decodeFromString(LicenseRecord.serializer(), json)

        assertEquals(
            record1,
            record2,
            "equals/hashcode must be implemented properly as we are using HashSets later to ensure that licenses are only printed once to the reports"
        )
    }

    @Test
    fun parseLicenses() {
        val entries = LicenseEntry.readFromStream(LicenseEntry::class.java.classLoader.getResourceAsStream("license/licenses.json"))
        assertEquals(2, entries.size)
    }
}
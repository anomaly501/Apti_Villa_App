package app.apti.villa.importer

import app.apti.villa.core.common.AppError
import app.apti.villa.core.common.Outcome
import app.apti.villa.domain.importer.IssueCodes
import app.apti.villa.domain.importer.JsonParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonParserTest {

    private val parser = JsonParser()

    @Test
    fun `parse valid json succeeds`() {
        val json = """{"schema_version": 1, "source_metadata": {}}"""
        val outcome = parser.parse(json)
        assertTrue(outcome is Outcome.Success)
        val data = (outcome as Outcome.Success).data
        assertEquals(1, data["schema_version"].toString().toInt())
    }

    @Test
    fun `parse malformed json reports line and column`() {
        val json = """{
            "schema_version": 1,
            "broken": 
        }"""
        val outcome = parser.parse(json)
        assertTrue(outcome is Outcome.Error)
        val err = (outcome as Outcome.Error).error as AppError.Import
        val issue = err.issues.first()
        assertEquals(IssueCodes.E_JSON_SYNTAX, issue.code)
    }

    @Test
    fun `parse non-object root returns wrong type error`() {
        val json = """["an", "array"]"""
        val outcome = parser.parse(json)
        assertTrue(outcome is Outcome.Error)
        val err = (outcome as Outcome.Error).error as AppError.Import
        assertEquals(IssueCodes.E_WRONG_TYPE, err.issues.first().code)
    }
}

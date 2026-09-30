package app.apti.villa.importer

import app.apti.villa.core.common.Outcome
import app.apti.villa.domain.importer.ContentValidator
import app.apti.villa.domain.importer.IssueCodes
import app.apti.villa.domain.importer.JsonParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ContentValidatorTest {

    private val parser = JsonParser()
    private val validator = ContentValidator()

    private fun loadFixture(name: String): String {
        return javaClass.classLoader?.getResourceAsStream("fixtures/$name")
            ?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
            ?: error("Fixture fixtures/$name not found on classpath")
    }

    @Test
    fun `valid_minimal fixture parses and produces valid plan with no errors`() {
        val json = loadFixture("valid_minimal.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertFalse(plan.hasErrors)
        assertEquals(1, plan.validQuestions.size)
        assertEquals(0, plan.skippedQuestions.size)
    }

    @Test
    fun `valid_full fixture parses all optional fields`() {
        val json = loadFixture("valid_full.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertFalse(plan.hasErrors)
        assertEquals(1, plan.validQuestions.size)
        val q = plan.validQuestions.first()
        assertEquals("Q1", q.externalId)
        assertEquals(2, q.hints.size)
        assertEquals(1, q.tricks.size)
        assertEquals(2, q.solutionSteps.size)
        assertEquals(330, q.sourceTimestampSec)
    }

    @Test
    fun `e_missing_field fixture emits E_MISSING_FIELD`() {
        val json = loadFixture("e_missing_field.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_MISSING_FIELD })
    }

    @Test
    fun `e_wrong_type fixture emits E_WRONG_TYPE`() {
        val json = loadFixture("e_wrong_type.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_WRONG_TYPE })
    }

    @Test
    fun `e_dup_question_id fixture emits E_DUP_QUESTION_ID`() {
        val json = loadFixture("e_dup_question_id.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_DUP_QUESTION_ID })
    }

    @Test
    fun `e_empty_question_text fixture emits E_EMPTY_QUESTION_TEXT`() {
        val json = loadFixture("e_empty_question_text.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_EMPTY_QUESTION_TEXT })
    }

    @Test
    fun `e_options_count fixture emits E_OPTIONS_COUNT`() {
        val json = loadFixture("e_options_count.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_OPTIONS_COUNT })
    }

    @Test
    fun `e_dup_option fixture emits E_DUP_OPTION`() {
        val json = loadFixture("e_dup_option.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_DUP_OPTION })
    }

    @Test
    fun `e_empty_option fixture emits E_EMPTY_OPTION`() {
        val json = loadFixture("e_empty_option.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_EMPTY_OPTION })
    }

    @Test
    fun `e_answer_no_match fixture emits E_ANSWER_NO_MATCH`() {
        val json = loadFixture("e_answer_no_match.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_ANSWER_NO_MATCH })
    }

    @Test
    fun `e_answer_ambiguous fixture emits E_ANSWER_AMBIGUOUS`() {
        val json = loadFixture("e_answer_ambiguous.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_ANSWER_AMBIGUOUS })
    }

    @Test
    fun `e_hint_numbering fixture emits E_HINT_NUMBERING`() {
        val json = loadFixture("e_hint_numbering.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_HINT_NUMBERING })
    }

    @Test
    fun `e_hint_count_mismatch fixture emits E_HINT_COUNT_MISMATCH`() {
        val json = loadFixture("e_hint_count_mismatch.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_HINT_COUNT_MISMATCH })
    }

    @Test
    fun `e_step_numbering fixture emits E_STEP_NUMBERING`() {
        val json = loadFixture("e_step_numbering.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_STEP_NUMBERING })
    }

    @Test
    fun `e_bad_url fixture emits E_BAD_URL`() {
        val json = loadFixture("e_bad_url.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_BAD_URL })
    }

    @Test
    fun `e_bad_timestamp fixture emits E_BAD_TIMESTAMP`() {
        val json = loadFixture("e_bad_timestamp.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_BAD_TIMESTAMP })
    }

    @Test
    fun `e_unsupported_structure fixture emits E_UNSUPPORTED_STRUCTURE`() {
        val json = loadFixture("e_unsupported_structure.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_UNSUPPORTED_STRUCTURE })
    }

    @Test
    fun `e_schema_version fixture emits E_SCHEMA_VERSION`() {
        val json = loadFixture("e_schema_version.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.E_SCHEMA_VERSION })
    }

    @Test
    fun `mixed_valid_invalid fixture produces both valid and skipped questions`() {
        val json = loadFixture("mixed_valid_invalid.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertTrue(plan.hasErrors)
        assertEquals(2, plan.validQuestions.size)
        assertEquals(1, plan.skippedQuestions.size)
    }

    @Test
    fun `w_unknown_field fixture emits W_UNKNOWN_FIELD and passes`() {
        val json = loadFixture("w_unknown_field.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertFalse(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.W_UNKNOWN_FIELD })
    }

    @Test
    fun `w_answer_letter fixture emits W_ANSWER_LETTER_MAPPED and passes`() {
        val json = loadFixture("w_answer_letter.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertFalse(plan.hasErrors)
        assertTrue(plan.issues.any { it.code == IssueCodes.W_ANSWER_LETTER_MAPPED })
        assertEquals(2, plan.validQuestions.first().options.first { it.isCorrect }.position)
    }

    @Test
    fun `hindi_content fixture parses Unicode Devanagari text correctly`() {
        val json = loadFixture("hindi_content.json")
        val parsed = (parser.parse(json) as Outcome.Success).data
        val plan = validator.validate(parsed)

        assertFalse(plan.hasErrors)
        assertEquals(1, plan.validQuestions.size)
        assertEquals("प्रतिशत", plan.sourceMetadata.topic)
        assertTrue(plan.validQuestions.first().text.contains("संख्या"))
    }
}

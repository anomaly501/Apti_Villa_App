package app.apti.villa.importer

import app.apti.villa.core.common.Outcome
import app.apti.villa.domain.importer.ContentValidator
import app.apti.villa.domain.importer.JsonParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.system.measureTimeMillis

class ImportBenchmarkTest {

    private val parser = JsonParser()
    private val validator = ContentValidator()

    @Test
    fun `benchmark parsing and validating 2000 questions takes less than 3000 ms`() {
        val sb = StringBuilder()
        sb.append("""{"schema_version":1,"source_metadata":{"title":"Benchmark Test","topic":"Speed"},"questions":[""")
        for (i in 1..2000) {
            if (i > 1) sb.append(",")
            sb.append("""{"id":"$i","question_text":"Question text number $i with some calculation details?","options":["Option A $i","Option B $i","Option C $i","Option D $i"],"correct_answer":"Option B $i","difficulty":"medium"}""")
        }
        sb.append("]}")
        val json = sb.toString()

        val elapsed = measureTimeMillis {
            val parseOutcome = parser.parse(json)
            assertTrue(parseOutcome is Outcome.Success)
            val root = (parseOutcome as Outcome.Success).data
            val plan = validator.validate(root)
            assertFalse(plan.hasErrors)
            assertEquals(2000, plan.validQuestions.size)
        }

        println("Time to parse and validate 2,000 questions: $elapsed ms")
        assertTrue("Expected < 3000 ms, took $elapsed ms", elapsed < 3000)
    }
}

package app.apti.villa.domain

import app.apti.villa.domain.model.Exam
import app.apti.villa.domain.model.Subject
import app.apti.villa.domain.model.SubTopic
import app.apti.villa.domain.model.Topic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class TaxonomyModelTest {

    @Test
    fun `exam creation has default values`() {
        val uuid = UUID.randomUUID().toString()
        val exam = Exam(uuid = uuid, name = "SSC CGL")

        assertEquals(uuid, exam.uuid)
        assertEquals("SSC CGL", exam.name)
        assertTrue(exam.isBuiltin)
        assertEquals(0, exam.sortOrder)
    }

    @Test
    fun `topic and subtopic hierarchy is well structured`() {
        val subject = Subject(id = 1L, name = "Quantitative Aptitude")
        val topic = Topic(id = 10L, subjectId = subject.id, name = "Arithmetic")
        val subTopic = SubTopic(id = 100L, topicId = topic.id, name = "Percentage")

        assertEquals(1L, topic.subjectId)
        assertEquals(10L, subTopic.topicId)
    }
}

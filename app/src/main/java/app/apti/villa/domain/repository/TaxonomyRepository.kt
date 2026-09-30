package app.apti.villa.domain.repository

import app.apti.villa.domain.model.Exam
import app.apti.villa.domain.model.Subject
import app.apti.villa.domain.model.SubTopic
import app.apti.villa.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface TaxonomyRepository {
    fun getAllExams(): Flow<List<Exam>>
    suspend fun getExamByUuid(uuid: String): Exam?
    suspend fun getExamByName(name: String): Exam?
    suspend fun upsertExam(exam: Exam): Long
    suspend fun deleteExam(exam: Exam)

    fun getAllSubjects(): Flow<List<Subject>>
    suspend fun getSubjectById(id: Long): Subject?
    suspend fun getSubjectByName(name: String): Subject?
    suspend fun insertSubject(subject: Subject): Long

    fun getAllTopics(): Flow<List<Topic>>
    fun getTopicsBySubject(subjectId: Long): Flow<List<Topic>>
    suspend fun getTopicById(id: Long): Topic?
    suspend fun getTopicBySubjectAndName(subjectId: Long?, name: String): Topic?
    suspend fun insertTopic(topic: Topic): Long

    fun getSubTopicsByTopic(topicId: Long): Flow<List<SubTopic>>
    suspend fun getSubTopicById(id: Long): SubTopic?
    suspend fun getSubTopicByTopicAndName(topicId: Long, name: String): SubTopic?
    suspend fun insertSubTopic(subTopic: SubTopic): Long
}

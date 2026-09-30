package app.apti.villa.data.repository

import app.apti.villa.data.local.dao.ExamDao
import app.apti.villa.data.local.dao.SubjectDao
import app.apti.villa.data.local.dao.SubTopicDao
import app.apti.villa.data.local.dao.TopicDao
import app.apti.villa.data.local.entity.ExamEntity
import app.apti.villa.data.local.entity.SubjectEntity
import app.apti.villa.data.local.entity.SubTopicEntity
import app.apti.villa.data.local.entity.TopicEntity
import app.apti.villa.domain.model.Exam
import app.apti.villa.domain.model.Subject
import app.apti.villa.domain.model.SubTopic
import app.apti.villa.domain.model.Topic
import app.apti.villa.domain.repository.TaxonomyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaxonomyRepositoryImpl @Inject constructor(
    private val examDao: ExamDao,
    private val subjectDao: SubjectDao,
    private val topicDao: TopicDao,
    private val subTopicDao: SubTopicDao,
) : TaxonomyRepository {

    override fun getAllExams(): Flow<List<Exam>> {
        return examDao.getAllExams().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getExamByUuid(uuid: String): Exam? {
        return examDao.getExamByUuid(uuid)?.toDomain()
    }

    override suspend fun getExamByName(name: String): Exam? {
        return examDao.getExamByName(name)?.toDomain()
    }

    override suspend fun upsertExam(exam: Exam): Long {
        return examDao.upsert(exam.toEntity())
    }

    override suspend fun deleteExam(exam: Exam) {
        examDao.delete(exam.toEntity())
    }

    override fun getAllSubjects(): Flow<List<Subject>> {
        return subjectDao.getAllSubjects().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getSubjectById(id: Long): Subject? {
        return subjectDao.getSubjectById(id)?.toDomain()
    }

    override suspend fun getSubjectByName(name: String): Subject? {
        return subjectDao.getSubjectByName(name)?.toDomain()
    }

    override suspend fun insertSubject(subject: Subject): Long {
        return subjectDao.insert(subject.toEntity())
    }

    override fun getAllTopics(): Flow<List<Topic>> {
        return topicDao.getAllTopics().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTopicsBySubject(subjectId: Long): Flow<List<Topic>> {
        return topicDao.getTopicsBySubject(subjectId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTopicById(id: Long): Topic? {
        return topicDao.getTopicById(id)?.toDomain()
    }

    override suspend fun getTopicBySubjectAndName(subjectId: Long?, name: String): Topic? {
        return topicDao.getTopicBySubjectAndName(subjectId, name)?.toDomain()
    }

    override suspend fun insertTopic(topic: Topic): Long {
        return topicDao.insert(topic.toEntity())
    }

    override fun getSubTopicsByTopic(topicId: Long): Flow<List<SubTopic>> {
        return subTopicDao.getSubTopicsByTopic(topicId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getSubTopicById(id: Long): SubTopic? {
        return subTopicDao.getSubTopicById(id)?.toDomain()
    }

    override suspend fun getSubTopicByTopicAndName(topicId: Long, name: String): SubTopic? {
        return subTopicDao.getSubTopicByTopicAndName(topicId, name)?.toDomain()
    }

    override suspend fun insertSubTopic(subTopic: SubTopic): Long {
        return subTopicDao.insert(subTopic.toEntity())
    }

    private fun ExamEntity.toDomain() = Exam(
        id = id,
        uuid = uuid,
        name = name,
        isBuiltin = isBuiltin,
        sortOrder = sortOrder,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun Exam.toEntity() = ExamEntity(
        id = id,
        uuid = uuid,
        name = name,
        isBuiltin = isBuiltin,
        sortOrder = sortOrder,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun SubjectEntity.toDomain() = Subject(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun Subject.toEntity() = SubjectEntity(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun TopicEntity.toDomain() = Topic(
        id = id,
        subjectId = subjectId,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun Topic.toEntity() = TopicEntity(
        id = id,
        subjectId = subjectId,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun SubTopicEntity.toDomain() = SubTopic(
        id = id,
        topicId = topicId,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun SubTopic.toEntity() = SubTopicEntity(
        id = id,
        topicId = topicId,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

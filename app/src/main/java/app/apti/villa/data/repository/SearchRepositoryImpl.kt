package app.apti.villa.data.repository

import app.apti.villa.core.common.IoDispatcher
import app.apti.villa.data.local.dao.SearchDao
import app.apti.villa.data.local.dao.SearchDbResult
import app.apti.villa.domain.engine.search.SearchQuerySanitizer
import app.apti.villa.domain.model.SearchCategory
import app.apti.villa.domain.model.SearchResultItem
import app.apti.villa.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val searchDao: SearchDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : SearchRepository {

    override suspend fun search(rawQuery: String, category: SearchCategory): List<SearchResultItem> = withContext(ioDispatcher) {
        val ftsQuery = SearchQuerySanitizer.sanitizeForFts(rawQuery)
        if (ftsQuery.isBlank()) return@withContext emptyList()

        val results = mutableListOf<SearchDbResult>()

        when (category) {
            SearchCategory.ALL -> {
                // Questions
                val questions = searchDao.searchQuestionsOnce(ftsQuery)
                val options = searchDao.searchQuestionsByOptionsOnce(ftsQuery)
                val questionIds = questions.map { it.id }.toSet()
                results.addAll(questions)
                results.addAll(options.filter { it.id !in questionIds })

                // Tests
                results.addAll(searchDao.searchTestsOnce(ftsQuery))

                // Tricks
                results.addAll(searchDao.searchTricksOnce(ftsQuery))

                // Topics
                results.addAll(searchDao.searchTopicsOnce("%${rawQuery.trim()}%"))
            }
            SearchCategory.QUESTION -> {
                val questions = searchDao.searchQuestionsOnce(ftsQuery)
                val options = searchDao.searchQuestionsByOptionsOnce(ftsQuery)
                val questionIds = questions.map { it.id }.toSet()
                results.addAll(questions)
                results.addAll(options.filter { it.id !in questionIds })
            }
            SearchCategory.TEST -> {
                results.addAll(searchDao.searchTestsOnce(ftsQuery))
            }
            SearchCategory.TRICK -> {
                results.addAll(searchDao.searchTricksOnce(ftsQuery))
            }
            SearchCategory.TOPIC -> {
                results.addAll(searchDao.searchTopicsOnce("%${rawQuery.trim()}%"))
            }
        }

        results.map { row ->
            val cat = when (row.resultType) {
                "TEST" -> SearchCategory.TEST
                "TRICK" -> SearchCategory.TRICK
                "TOPIC" -> SearchCategory.TOPIC
                else -> SearchCategory.QUESTION
            }
            SearchResultItem(
                id = row.id,
                title = row.title,
                category = cat,
                snippet = row.snippet,
                subjectName = row.subjectName,
                topicName = row.topicName,
                difficulty = row.difficulty,
            )
        }
    }

    override fun searchFlow(rawQuery: String, category: SearchCategory): Flow<List<SearchResultItem>> = flow {
        emit(search(rawQuery, category))
    }.flowOn(ioDispatcher)
}

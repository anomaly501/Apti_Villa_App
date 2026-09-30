package app.apti.villa.domain.model

enum class SearchCategory {
    ALL,
    QUESTION,
    TEST,
    TRICK,
    TOPIC,
}

data class SearchResultItem(
    val id: Long,
    val title: String,
    val category: SearchCategory,
    val snippet: String? = null,
    val subjectName: String? = null,
    val topicName: String? = null,
    val difficulty: String? = null,
)

data class SearchFilter(
    val query: String = "",
    val category: SearchCategory = SearchCategory.ALL,
)

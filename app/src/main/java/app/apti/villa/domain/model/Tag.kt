package app.apti.villa.domain.model

data class Tag(
    val id: Long = 0,
    val uuid: String,
    val name: String,
    val color: String? = null,
    val isSystem: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)


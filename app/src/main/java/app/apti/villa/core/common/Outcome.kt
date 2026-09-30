package app.apti.villa.core.common

import app.apti.villa.domain.importer.ImportIssue

/**
 * A sealed interface representing the outcome of an operation.
 * Used throughout the app for consistent error handling.
 */
sealed interface Outcome<out T> {
    data class Success<T>(val data: T) : Outcome<T>
    data class Error(val error: AppError) : Outcome<Nothing>
}

/**
 * Sealed hierarchy of app errors. Each feature can extend with specific subtypes.
 */
sealed interface AppError {
    val message: String

    data class Import(
        val issues: List<ImportIssue>,
        override val message: String = issues.firstOrNull()?.message ?: "Import failed",
    ) : AppError

    data class Database(
        val cause: Throwable? = null,
        override val message: String = cause?.message ?: "Database error",
    ) : AppError

    data class Ai(
        override val message: String,
        val code: String? = null,
    ) : AppError

    data class Keystore(
        val cause: Throwable? = null,
        override val message: String = cause?.message ?: "Keystore error",
    ) : AppError

    data class Unknown(
        val cause: Throwable? = null,
        override val message: String = cause?.message ?: "Unknown error",
    ) : AppError
}

/**
 * Convenience extension to map Success data.
 */
inline fun <T, R> Outcome<T>.map(transform: (T) -> R): Outcome<R> = when (this) {
    is Outcome.Success -> Outcome.Success(transform(data))
    is Outcome.Error -> this
}

/**
 * Convenience extension to get data or null.
 */
fun <T> Outcome<T>.getOrNull(): T? = when (this) {
    is Outcome.Success -> data
    is Outcome.Error -> null
}

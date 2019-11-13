package net.hulyka.spacexviewer.domain.base

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Result] are result an instance of [Failure] or [Success].
 * FP Convention dictates that [Failure] is used for "failure"
 * and [Success] is used for "success".
 *
 * @see Failure
 * @see Success
 */
sealed class Result<out L, out R> {
    data class Failure<out L>(val a: L) : Result<L, Nothing>()

    data class Success<out R>(val b: R) : Result<Nothing, R>()

    val isSuccess get() = this is Success<R>
    val isFailure get() = this is Failure<L>

    fun <L> failure(a: L) = Failure(a)
    fun <R> success(b: R) = Success(b)

    fun result(fnF: (L) -> Any, fnS: (R) -> Any): Any =
        when (this) {
            is Failure -> fnF(a)
            is Success -> fnS(b)
        }
}
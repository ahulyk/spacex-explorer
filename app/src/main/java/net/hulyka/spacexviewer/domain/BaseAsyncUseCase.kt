package net.hulyka.spacexviewer.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.hulyka.spacexviewer.domain.base.Failure
import net.hulyka.spacexviewer.domain.base.Result

abstract class BaseAsyncUseCase<out T, in P> : BaseUseCase<Any?, Any?>() where T : Any {

    abstract suspend fun run(params: P): Result<Failure, T>

    operator fun invoke(
        scope: CoroutineScope,
        params: P,
        onResult: (Result<Failure, T>) -> Unit = {}
    ) {
        scope.launch {
            onResult(
                try {
                    withContext(IO) { run(params) }
                } catch (e: Exception) {
                    mapFailure(e)
                }
            )
        }
    }

}
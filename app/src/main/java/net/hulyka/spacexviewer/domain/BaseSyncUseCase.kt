package net.hulyka.spacexviewer.domain

import net.hulyka.spacexviewer.domain.base.Failure
import net.hulyka.spacexviewer.domain.base.Result
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseSyncUseCase<out T, in P> : BaseUseCase<Any?, Any?>() where T : Any {

    abstract fun run(params: P): Result<Failure, T>

    operator fun invoke(
        params: P,
        onResult: (Result<Failure, T>) -> Unit = {}
    ) {
        onResult(
            try {
                run(params)
            } catch (e: Exception) {
                mapFailure(e)
            }
        )
    }


}
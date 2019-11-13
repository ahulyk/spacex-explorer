package net.hulyka.spacexviewer.domain

import net.hulyka.spacexviewer.domain.base.Failure
import net.hulyka.spacexviewer.domain.base.Result
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseUseCase<T, U> {

    protected fun mapFailure(e: Exception) =
        when (e) {
            is HttpException -> Failure.ServerError(e)
            is UnknownHostException, is SocketTimeoutException -> Failure.NetworkConnection(e)
            else -> Failure.FeatureFailure(e)
        }
            .run {
                Timber.w(e)
                Result.Failure(this)
            }

}
package net.hulyka.spacexviewer.domain.base

sealed class Failure(val exception: Exception = Exception("Failure")) {

    class NetworkConnection(e: Exception) : Failure(e)
    class ServerError(e: Exception) : Failure(e)
    class FeatureFailure(e: Exception) : Failure(e)

}
package net.hulyka.spacexviewer.domain

import net.hulyka.spacexviewer.domain.base.Failure
import net.hulyka.spacexviewer.domain.base.Result
import net.hulyka.spacexviewer.domain.model.LaunchInfo
import net.hulyka.spacexviewer.repository.LaunchRepository
import javax.inject.Inject

class LoadLaunchesUseCase @Inject constructor(private val launchRepository: LaunchRepository) :
    BaseAsyncUseCase<List<LaunchInfo>, LoadLaunchesUseCase.Params>() {

    override suspend fun run(params: Params): Result<Failure, List<LaunchInfo>> {
        return Result.Success(launchRepository.getAllLaunches(params.limit, params.offset))
    }

    data class Params(val limit: Int, val offset: Int)

}
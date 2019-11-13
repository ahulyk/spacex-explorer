package net.hulyka.spacexviewer.domain

import net.hulyka.spacexviewer.api.model.LaunchData
import net.hulyka.spacexviewer.domain.base.Failure
import net.hulyka.spacexviewer.domain.base.Result
import net.hulyka.spacexviewer.domain.model.LaunchInfo
import net.hulyka.spacexviewer.repository.LaunchRepository
import javax.inject.Inject

class SaveDetailLaunchUseCase @Inject constructor(private val launchRepository: LaunchRepository) :
    BaseSyncUseCase<Unit, SaveDetailLaunchUseCase.Params>() {

    override fun run(params: Params): Result<Failure, Unit> {
        launchRepository.detailLaunchItem = params.launchData
        return Result.Success(Unit)
    }

    data class Params(val launchData: LaunchInfo)

}
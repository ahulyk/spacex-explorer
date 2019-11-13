package net.hulyka.spacexviewer.domain

import net.hulyka.spacexviewer.api.model.LaunchData
import net.hulyka.spacexviewer.domain.base.Failure
import net.hulyka.spacexviewer.domain.base.Result
import net.hulyka.spacexviewer.domain.model.LaunchInfo
import net.hulyka.spacexviewer.repository.LaunchRepository
import javax.inject.Inject

class LoadDetailLaunchUseCase @Inject constructor(private val launchRepository: LaunchRepository) :
    BaseSyncUseCase<LaunchInfo, Unit>() {

    override fun run(params: Unit): Result<Failure, LaunchInfo> {
        launchRepository.detailLaunchItem?.let {
            return Result.Success(it)
        }
        return Result.Failure(Failure.FeatureFailure(IllegalStateException(("Can not load LaunchItem details"))))
    }

}
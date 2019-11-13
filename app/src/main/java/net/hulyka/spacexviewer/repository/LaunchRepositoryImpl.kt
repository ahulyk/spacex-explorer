package net.hulyka.spacexviewer.repository

import net.hulyka.spacexviewer.api.service.APIService
import net.hulyka.spacexviewer.domain.model.LaunchInfo
import net.hulyka.spacexviewer.mapper.LaunchInfoMapper
import javax.inject.Inject

class LaunchRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val launchInfoMapper: LaunchInfoMapper
) : LaunchRepository {

    override var detailLaunchItem: LaunchInfo? = null

    override suspend fun getAllLaunches(limit: Int, offset: Int): List<LaunchInfo> {
        return apiService.getAllLaunches(limit, offset).map { launchInfoMapper.toLaunchInfo(it) }
    }

    override suspend fun getPastLaunches(start: String, end: String): List<LaunchInfo> {
        return apiService.getPastLaunches(start, end).map { launchInfoMapper.toLaunchInfo(it) }
    }

}
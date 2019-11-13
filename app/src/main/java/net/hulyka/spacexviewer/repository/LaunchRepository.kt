package net.hulyka.spacexviewer.repository

import net.hulyka.spacexviewer.api.model.LaunchData
import net.hulyka.spacexviewer.domain.model.LaunchInfo

interface LaunchRepository {

    var detailLaunchItem: LaunchInfo?

    suspend fun getAllLaunches(limit: Int, offset: Int): List<LaunchInfo>

    suspend fun getPastLaunches(start: String, end: String): List<LaunchInfo>

}
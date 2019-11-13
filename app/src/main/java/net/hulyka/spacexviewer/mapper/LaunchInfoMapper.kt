package net.hulyka.spacexviewer.mapper

import net.hulyka.spacexviewer.api.model.LaunchData
import net.hulyka.spacexviewer.domain.model.LaunchInfo

interface LaunchInfoMapper {

    fun toLaunchInfo(dto: LaunchData): LaunchInfo

}
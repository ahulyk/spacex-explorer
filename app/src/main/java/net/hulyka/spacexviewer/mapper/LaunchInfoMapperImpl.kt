package net.hulyka.spacexviewer.mapper

import android.content.Context
import net.hulyka.spacexviewer.R
import net.hulyka.spacexviewer.api.model.LaunchData
import net.hulyka.spacexviewer.domain.model.LaunchInfo
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatter.ofLocalizedDateTime
import org.threeten.bp.format.FormatStyle
import javax.inject.Inject

class LaunchInfoMapperImpl @Inject constructor(val context: Context) : LaunchInfoMapper {

    override fun toLaunchInfo(data: LaunchData): LaunchInfo {
        return LaunchInfo(
            details = createDetails(data),
            flightNumber = data.flightNumber,
            launchDate = data.launchDate,
            launchDateFormattedMedium = data.launchDate.format(ofLocalizedDateTime(FormatStyle.MEDIUM)),
            launchDateFormattedSmall = data.launchDate.format(DateTimeFormatter.ISO_DATE),
            missionName = data.missionName,
            failureReason = data.failureDetails?.reason,
            launchStatus = getStatus(data),
            links = LaunchInfo.Links(
                articleLink = data.links.articleLink,
                flickrImages = data.links.flickrImages,
                missionPatch = data.links.missionPatch,
                wikipedia = data.links.wikipedia
            )
        )
    }

    private fun getStatus(data: LaunchData): LaunchInfo.Status {
        return when {
            data.launchSuccess == null && data.launchDate.isAfter(LocalDateTime.now()) -> LaunchInfo.Status.UPCOMMING
            data.launchSuccess != null && data.launchSuccess -> LaunchInfo.Status.SUCCESS
            else -> LaunchInfo.Status.FAILURE
        }
    }

    private fun createDetails(dto: LaunchData) =
        dto.details ?: if (dto.launchDate.isAfter(LocalDateTime.now())) {
            context.getString(R.string.no_details_available_future)
        } else {
            context.getString(R.string.no_details_available)
        }
}
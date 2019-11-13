package net.hulyka.spacexviewer.domain.model


import org.threeten.bp.LocalDateTime

data class LaunchInfo(
    val details: String,
    val flightNumber: Int,
    val launchDate: LocalDateTime,
    val launchDateFormattedMedium: String,
    val launchDateFormattedSmall: String,
    val links: Links,
    val missionName: String,
    val failureReason: String?,
    val launchStatus: Status
) {
    data class Links(
        val articleLink: String? = null,
        val flickrImages: List<String> = listOf(),
        val missionPatch: String? = null,
        val videoLink: String? = null,
        val wikipedia: String? = null
    )

    enum class Status {
        SUCCESS,
        FAILURE,
        UPCOMMING
    }

}
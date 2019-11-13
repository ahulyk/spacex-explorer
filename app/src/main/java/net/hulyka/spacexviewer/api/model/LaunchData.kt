package net.hulyka.spacexviewer.api.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.hulyka.spacexviewer.json.DateSerializer
import org.threeten.bp.LocalDateTime

@Serializable
data class LaunchData(
    @SerialName("details")
    val details: String?,
    @SerialName("flight_number")
    val flightNumber: Int,
    @Serializable(with = DateSerializer::class)
    @SerialName("launch_date_unix")
    val launchDate: LocalDateTime,
    @SerialName("links")
    val links: Links,
    @SerialName("mission_name")
    val missionName: String,
    @SerialName("launch_failure_details")
    val failureDetails: FailureDetails? = null,
    @SerialName("launch_success")
    val launchSuccess: Boolean? = false
) {

    @Serializable
    data class Links(
        @SerialName("article_link")
        val articleLink: String? = null,
        @SerialName("flickr_images")
        val flickrImages: List<String> = listOf(),
        @SerialName("mission_patch")
        val missionPatch: String? = null,
        @SerialName("mission_patch_small")
        val missionPatchSmall: String? = null,
        @SerialName("video_link")
        val videoLink: String? = null,
        @SerialName("wikipedia")
        val wikipedia: String? = null
    )

    @Serializable
    data class FailureDetails(
        @SerialName("reason")
        val reason: String? = null
    )

}
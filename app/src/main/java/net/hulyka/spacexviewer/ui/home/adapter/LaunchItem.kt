package net.hulyka.spacexviewer.ui.home.adapter

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import net.hulyka.spacexviewer.R
import net.hulyka.spacexviewer.domain.model.LaunchInfo
import net.hulyka.spacexviewer.loadImage

class LaunchItem(private val launchInfo: LaunchInfo) : ILaunchItem {

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        onItemClickListener: ILaunchItem.OnItemClickListener?
    ) {
        holder as LaunchItemViewHolder
        holder.title.text = launchInfo.missionName
        holder.subtitle.text = launchInfo.details
        holder.date.text = launchInfo.launchDateFormattedSmall
        holder.icon.loadImage(launchInfo.links.missionPatch, R.drawable.ic_spacex_logo_xonly) {}
        holder.itemView.setOnClickListener {
            ViewCompat.setTransitionName(holder.icon, "imageView")
            onItemClickListener?.onClick(
                holder.icon,
                launchInfo
            )
        }
    }

    override val viewType = ILaunchItem.ViewType.LAUNCH_ITEM

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LaunchItem

        if (launchInfo != other.launchInfo) return false
        if (viewType != other.viewType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = launchInfo.hashCode()
        result = 31 * result + viewType.hashCode()
        return result
    }

}
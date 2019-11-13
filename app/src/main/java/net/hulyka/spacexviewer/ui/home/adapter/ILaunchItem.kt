package net.hulyka.spacexviewer.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import net.hulyka.spacexviewer.domain.model.LaunchInfo

interface ILaunchItem {

    enum class ViewType {

        LAUNCH_ITEM,
        HEADER,
        DELIMITER;

        companion object {
            fun getByViewTypeId(viewType: Int): ViewType = values()[viewType]
        }

    }

    interface OnItemClickListener {
        fun onClick(v: View, launchInfo: LaunchInfo)
    }

    fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        onItemClickListener: OnItemClickListener?
    )

    val viewType: ViewType

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

}

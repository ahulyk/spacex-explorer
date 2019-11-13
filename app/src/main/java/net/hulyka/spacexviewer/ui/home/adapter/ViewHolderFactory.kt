package net.hulyka.spacexviewer.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.hulyka.spacexviewer.R
import net.hulyka.spacexviewer.inflate
import net.hulyka.spacexviewer.ui.home.adapter.ILaunchItem.ViewType.*

class ViewHolderFactory {

    fun createViewHolder(
        viewType: ILaunchItem.ViewType,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            LAUNCH_ITEM -> LaunchItemViewHolder(parent.inflate(R.layout.fragment_home_item_main))
            HEADER -> TODO("Implement header item + sort")
            DELIMITER -> TODO("Delimiter")
        }
    }

}

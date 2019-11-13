package net.hulyka.spacexviewer.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil

object DiffUtilCallback : DiffUtil.ItemCallback<ILaunchItem>() {

    override fun areItemsTheSame(item0: ILaunchItem, item1: ILaunchItem) = item0 == item1

    override fun areContentsTheSame(item0: ILaunchItem, item1: ILaunchItem) = item0 == item1

}
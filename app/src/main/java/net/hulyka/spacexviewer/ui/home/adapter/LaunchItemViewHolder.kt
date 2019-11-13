package net.hulyka.spacexviewer.ui.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home_item_main.view.*

class LaunchItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val title: TextView = v.titleTxt
    val subtitle: TextView = v.subtitleTxt
    val date: TextView = v.dateTxt
    val icon: ImageView = v.iconImg
}
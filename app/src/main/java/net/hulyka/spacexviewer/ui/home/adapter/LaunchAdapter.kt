package net.hulyka.spacexviewer.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class LaunchAdapter(private val onItemClickListener: ILaunchItem.OnItemClickListener) :
    ListAdapter<ILaunchItem, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private val vhFactory = ViewHolderFactory()

    override fun onCreateViewHolder(parent: ViewGroup, viewTypeId: Int): RecyclerView.ViewHolder {
        return vhFactory.createViewHolder(ILaunchItem.ViewType.getByViewTypeId(viewTypeId), parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).onBindViewHolder(holder, onItemClickListener)
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    fun setItems(items: List<ILaunchItem>) {
        submitList(items)
    }

    fun clearItems() {
        submitList(null)
    }

}

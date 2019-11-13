package net.hulyka.spacexviewer.ui.details

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_details_image_item.view.*
import net.hulyka.spacexviewer.R
import net.hulyka.spacexviewer.hide
import net.hulyka.spacexviewer.inflate
import net.hulyka.spacexviewer.loadImage

class DetailImageAdapter : RecyclerView.Adapter<DetailImageAdapter.ImageViewHolder>() {

    private val imageRes = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(parent.inflate(R.layout.fragment_details_image_item, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.image.loadImage(imageRes[position]) {
            holder.progressBar.hide()
        }
    }

    override fun getItemCount(): Int {
        return imageRes.size
    }

    fun setItems(items: List<String>) {
        imageRes.clear()
        imageRes.addAll(items)
        notifyDataSetChanged()
    }

    class ImageViewHolder(v: View) :
        RecyclerView.ViewHolder(v) {
        val image: ImageView = v.itemImg
        val progressBar: ProgressBar = v.progressBar
    }

}
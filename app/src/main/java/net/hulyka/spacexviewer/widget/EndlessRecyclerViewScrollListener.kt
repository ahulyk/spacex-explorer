package net.hulyka.spacexviewer.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener {

    private val layoutManager: RecyclerView.LayoutManager
    // The minimum amountToDisplay of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 15
    // The current offset index of data you have loaded
    private var currentPage = 1
    // The base number of items in the dataset after the last loadWithSpacexPlaceholder
    private var previousTotalItemCount = 0
    // True if we are still waiting for the last set of data to loadWithSpacexPlaceholder.
    private var loading = true
    // Sets the starting page index
    private val startingPageIndex = 1

    constructor(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to loadWithSpacexPlaceholder some more data,
    // but first we check if we are waiting for the previous loadWithSpacexPlaceholder to finish.
    override fun onScrolled(
        view: RecyclerView,
        dx: Int,
        dy: Int
    ) {
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = when (layoutManager) {
            is StaggeredGridLayoutManager -> getLastVisibleItem(
                layoutManager.findLastVisibleItemPositions(null)
            )
            is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
            is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
            else -> 0
        }

        // If the base item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and base item count.

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many base columns there are too

        // If the base item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and base item count.
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many base columns there are too
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount)
            loading = true
        }
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(
        page: Int,
        totalItemsCount: Int
    )

}

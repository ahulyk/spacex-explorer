package net.hulyka.spacexviewer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.no_connection_white_layout.*
import net.hulyka.spacexviewer.*
import net.hulyka.spacexviewer.domain.model.LaunchInfo
import net.hulyka.spacexviewer.ui.home.adapter.ILaunchItem
import net.hulyka.spacexviewer.ui.home.adapter.LaunchAdapter
import net.hulyka.spacexviewer.viewmodel.ViewModelFactory
import net.hulyka.spacexviewer.widget.EndlessRecyclerViewScrollListener
import javax.inject.Inject


class HomeFragment : DaggerFragment(), ILaunchItem.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazyFast {
        viewModelProvider<HomeViewModel>(viewModelFactory)
    }

    private val adapter = LaunchAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        retryBtn.setOnClickListener {
            viewModel.reloadLaunchData()
        }
        viewModel.launchesData.observe(viewLifecycleOwner, Observer {
            when (it) {
                HomeViewModel.State.Loading -> {
                    contentLayout.show()
                    progressBar.show()
                    noConnectionLayout.hide()
                }
                is HomeViewModel.State.Success -> {
                    adapter.setItems(it.data)
                    contentLayout.show()
                    progressBar.hide()
                    noConnectionLayout.hide()
                }
                HomeViewModel.State.Error -> {
                    contentLayout.hide()
                    progressBar.hide()
                    noConnectionLayout.show()
                }
                HomeViewModel.State.NetworkError -> {
                    contentLayout.hide()
                    progressBar.hide()
                    noConnectionLayout.show()
                }
            }
        })

    }

    private fun setupRecyclerView() {
        val lm = LinearLayoutManager(requireContext(), VERTICAL, false)
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(lm) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                viewModel.loadLaunchData(totalItemsCount)
            }
        })
    }


    override fun onClick(v: View, launchInfo: LaunchInfo) {
        viewModel.saveDetailScreenData(launchInfo)
        Navigation.findNavController(root)
            .navigate(
                HomeFragmentDirections.actionNavigationHomeToDetailsFragment(),
                FragmentNavigatorExtras(v to "imageView")
            )
    }


}
package net.hulyka.spacexviewer.ui.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_chart.*
import kotlinx.android.synthetic.main.no_connection_white_layout.*
import net.hulyka.spacexviewer.*
import net.hulyka.spacexviewer.viewmodel.ViewModelFactory
import net.hulyka.spacexviewer.widget.SimpleGraph
import javax.inject.Inject

class ChartFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazyFast {
        viewModelProvider<ChartViewModel>(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_chart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        retryBtn.setOnClickListener {
            viewModel.loadLaunchData()
        }
        viewModel.launchesData.observe(viewLifecycleOwner, Observer {
            when (it) {
                ChartViewModel.State.Loading -> {
                    progressLayout.show()
                    noConnectionLayout.hide()
                }
                is ChartViewModel.State.Success -> {
                    contentLayout.show()
                    graph.setGraphData(it.data)
                    progressLayout.hide()
                    noConnectionLayout.hide()
                }
                ChartViewModel.State.Error -> {
                    noConnectionLayout.show()
                    progressLayout.hide()
                    contentLayout.hide()
                }
                ChartViewModel.State.NetworkError -> {
                    noConnectionLayout.show()
                    progressLayout.hide()
                    contentLayout.hide()
                }
            }
        })
        graph.listeter = object : SimpleGraph.OnTouchListener {
            override fun onTouch(v: View, value: Int) {
                requireContext().toast("Show some fancy view with launch q-ty: $value")
            }
        }
    }

}
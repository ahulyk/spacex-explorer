package net.hulyka.spacexviewer.ui.details

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.PagerSnapHelper
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details_cl.*
import net.hulyka.spacexviewer.*
import net.hulyka.spacexviewer.domain.model.LaunchInfo.Status.*
import net.hulyka.spacexviewer.viewmodel.ViewModelFactory
import javax.inject.Inject

class DetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazyFast {
        viewModelProvider<DetailsViewModel>(viewModelFactory)
    }
    private val adapter = DetailImageAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        postponeEnterTransition()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context)
                .inflateTransition(R.transition.image_shared_element_transition)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details_cl, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        motionLayout.progress = viewModel.motionLayoutProgress

        viewModel.launchesData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DetailsViewModel.State.Success -> {
                    loadMissionImage(it.launchInfo.links.missionPatch)
                    titleTxt.text = it.launchInfo.missionName
                    descriptionTxt.text = getString(
                        R.string.details_fragment_subtitle,
                        it.launchInfo.launchDateFormattedMedium
                    )
                    infoTxt.text = it.launchInfo.details
                    when (it.launchInfo.launchStatus) {
                        SUCCESS -> {
                            statusTxt.text = getString(R.string.details_fragment_success)
                            statusTxt.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_done_green_24dp,
                                0
                            )
                        }
                        FAILURE -> {
                            statusTxt.text = getString(R.string.details_fragment_failure)
                            statusTxt.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_error_outline_red_24dp,
                                0
                            )
                        }
                        UPCOMMING -> {
                            statusTxt.text = getString(R.string.details_fragment_upcoming)
                            statusTxt.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_access_time_yellow_24dp,
                                0
                            )
                        }
                    }
                    if (it.launchInfo.links.flickrImages.isNotEmpty()) {
                        adapter.setItems(it.launchInfo.links.flickrImages)
                    } else {
                        recyclerView.hide()
                        downloadImagesBtn.hide()
                    }
                }
                DetailsViewModel.State.Error -> {
                    context?.toast("Impl error state!")
                }
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        recyclerView.adapter = adapter
        PagerSnapHelper().attachToRecyclerView(recyclerView)

        downloadImagesBtn.setOnClickListener {
            viewModel.loadPhotos()
        }


    }

    override fun onDestroyView() {
        viewModel.motionLayoutProgress = motionLayout.progress
        super.onDestroyView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PERMISSION_GRANTED) {
            viewModel.loadPhotos()
        }
    }

    private fun loadMissionImage(url: String?) {
        missionImageView.loadImage(url, R.drawable.ic_spacex_logo_xonly) {
            startPostponedEnterTransition()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 21345
    }

}
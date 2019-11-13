package net.hulyka.spacexviewer.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.hulyka.spacexviewer.domain.LoadDetailLaunchUseCase
import net.hulyka.spacexviewer.domain.model.LaunchInfo
import net.hulyka.spacexviewer.repository.FileDownloadRepository
import net.hulyka.spacexviewer.repository.LaunchRepository
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val loadDetailLaunchUseCase: LoadDetailLaunchUseCase,
    private val fileDownloadRepository: FileDownloadRepository,
    private val launchRepository: LaunchRepository
) : ViewModel() {

    private val _launchesData: MutableLiveData<State> = MutableLiveData()
    val launchesData: LiveData<State> = _launchesData

    var motionLayoutProgress = 0f

    init {
        loadDetailLaunchData()
    }

    private fun loadDetailLaunchData() {
        loadDetailLaunchUseCase(Unit) {
            it.result({
                _launchesData.setValue(State.Error)
            }, {
                _launchesData.setValue(State.Success(it))
            })
        }
    }

    //TODO rewrite that to Work Manager + LiveData ??
    fun loadPhotos() {
        fileDownloadRepository.downloadFiles(launchRepository.detailLaunchItem?.links?.flickrImages.orEmpty())
    }

    sealed class State {
        data class Success(val launchInfo: LaunchInfo) : State()
        object Error : State()
    }

}
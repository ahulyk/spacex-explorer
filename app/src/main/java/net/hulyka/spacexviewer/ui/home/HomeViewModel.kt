package net.hulyka.spacexviewer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import net.hulyka.spacexviewer.domain.LoadLaunchesUseCase
import net.hulyka.spacexviewer.domain.SaveDetailLaunchUseCase
import net.hulyka.spacexviewer.domain.base.Failure
import net.hulyka.spacexviewer.domain.model.LaunchInfo
import net.hulyka.spacexviewer.ui.home.adapter.ILaunchItem
import net.hulyka.spacexviewer.ui.home.adapter.LaunchItem
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val loadLaunchesUseCase: LoadLaunchesUseCase,
    private val saveDetailLaunchUseCase: SaveDetailLaunchUseCase
) : ViewModel() {

    private val _launchesData: MutableLiveData<State> = MutableLiveData()
    val launchesData: LiveData<State> = _launchesData
    private val loadedItems = mutableListOf<LaunchInfo>()

    init {
        loadLaunchData(0)
    }

    fun reloadLaunchData() {
        loadLaunchData(loadedItems.size)
    }

    fun loadLaunchData(offset: Int) {
        _launchesData.value = State.Loading
        loadLaunchesUseCase(viewModelScope, LoadLaunchesUseCase.Params(25, offset)) {
            it.result(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(info: List<LaunchInfo>) {
        loadedItems.addAll(info)
        _launchesData.value = State.Success(loadedItems.map { LaunchItem(it) })
    }

    private fun handleFailure(f: Failure) {
        _launchesData.value = when (f) {
            is Failure.NetworkConnection -> State.NetworkError
            else -> State.Error
        }
    }

    fun saveDetailScreenData(launchInfo: LaunchInfo) {
        saveDetailLaunchUseCase(SaveDetailLaunchUseCase.Params(launchInfo)) {}
    }

    sealed class State {
        object Loading : State()
        data class Success(val data: List<ILaunchItem>) : State()
        object Error : State()
        object NetworkError : State()
    }

}
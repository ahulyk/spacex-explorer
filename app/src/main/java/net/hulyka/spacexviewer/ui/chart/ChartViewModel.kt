package net.hulyka.spacexviewer.ui.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import net.hulyka.spacexviewer.domain.LoadLaunchesForLastYearUseCase
import net.hulyka.spacexviewer.domain.base.Failure
import javax.inject.Inject

class ChartViewModel @Inject constructor(val loadLaunchesUseCase: LoadLaunchesForLastYearUseCase) :
    ViewModel() {


    private val _launchesData: MutableLiveData<State> = MutableLiveData()
    val launchesData: LiveData<State> = _launchesData

    init {
        loadLaunchData()
    }

    fun loadLaunchData() {
        _launchesData.value = State.Loading
        loadLaunchesUseCase(viewModelScope, Unit) {
            it.result(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(data: Map<String, Int>) {
        _launchesData.value = State.Success(data)
    }

    private fun handleFailure(f: Failure) {
        _launchesData.value = when (f) {
            is Failure.NetworkConnection -> State.NetworkError
            else -> State.Error
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val data: Map<String, Int>) : State()
        object Error : State()
        object NetworkError : State()
    }

}
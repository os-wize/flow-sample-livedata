package com.example.myprept.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprept.core.data.source.ReposRepository
import com.example.myprept.core.exception.Failure
import com.example.myprept.features.repos.model.Repo
import com.example.myprept.ui.common.UiStateViewModel
import com.example.myprept.utils.UiStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel
@Inject constructor(private val reposRepository: ReposRepository) : ViewModel(), UiStateViewModel {

    companion object {
        val DEBOUNCE_TIME_FOR_SEARCH = 500L
    }

    private val _uiState = MutableLiveData<UiStateManager.UiState>().apply {
        value = UiStateManager.UiState.INIT_EMPTY
    }
    override val uiState: LiveData<UiStateManager.UiState> = _uiState

    private val _repos: MutableLiveData<List<Repo>> = MutableLiveData()
    val repos: LiveData<List<Repo>> = _repos

    private val query = MutableStateFlow("")

    init {
        viewModelScope.launch {
            query
                .debounce(DEBOUNCE_TIME_FOR_SEARCH)
                .distinctUntilChanged()
                .onEach {
                    if (it.isNotEmpty()) {
                        getRepos(it)
                    }
                }
                .collect()
        }
    }

    fun searchRepo(repoQuery: String) {
        query.value = repoQuery
    }

     fun getRepos(q: String) {
        _uiState.value = UiStateManager.UiState.LOADING
        viewModelScope.launch {
            val result = reposRepository.getRepos(q)
            if(result.isLeft) {
                handleFailure()
            }
        }
    }

    private fun handleReposList(repos: List<Repo>) {
        if (repos.isEmpty()) {
            _uiState.value = UiStateManager.UiState.EMPTY

        } else {
            _uiState.value = UiStateManager.UiState.LOADED
        }

    }

    fun handleFailure() {
        _uiState.value = UiStateManager.UiState.ERROR
    }

}



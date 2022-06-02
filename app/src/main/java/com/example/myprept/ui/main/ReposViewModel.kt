package com.example.myprept.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprept.core.exception.Failure
import com.example.myprept.features.repos.model.GetReposUseCase
import com.example.myprept.features.repos.model.Repo
import com.example.myprept.ui.common.UiStateViewModel
import com.example.myprept.utils.UiStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel
@Inject constructor(private val getMovies: GetReposUseCase) : ViewModel(), UiStateViewModel {

    private val _uiState = MutableLiveData<UiStateManager.UiState>()
    override val uiState: LiveData<UiStateManager.UiState> = _uiState

    private val _repos: MutableLiveData<List<Repo>> = MutableLiveData()
    val repos: LiveData<List<Repo>> = _repos

    private val query = MutableStateFlow("")

    init {
        viewModelScope.launch {
            query
                .debounce(3000)
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
        getMovies(q, viewModelScope) { it.fold(::handleFailure, ::handleMovieList) }
    }

    private fun handleMovieList(repos: List<Repo>) {
        if (repos.isEmpty()) {
            _uiState.value = UiStateManager.UiState.EMPTY
        } else {
            _uiState.value = UiStateManager.UiState.LOADED
        }

    }

    fun handleFailure(failure: Failure) {
        _uiState.value = UiStateManager.UiState.ERROR
    }

}



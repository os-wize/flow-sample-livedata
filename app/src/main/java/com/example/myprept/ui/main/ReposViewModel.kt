package com.example.myprept.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprept.core.exception.Failure
import com.example.myprept.features.repos.model.GetReposUseCase
import com.example.myprept.features.repos.model.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReposViewModel
@Inject constructor(private val getMovies: GetReposUseCase) : ViewModel() {

    private val _repos: MutableLiveData<List<Repo>> = MutableLiveData()
    val repos: LiveData<List<Repo>> = _repos

    fun getRepos(q: String) =
        getMovies(q, viewModelScope) { it.fold(::handleFailure, ::handleMovieList) }


    private fun handleMovieList(movies: List<Repo>) {
        println(movies)

    }

    fun handleFailure(failure: Failure) {
        println(failure)
    }
}



package com.example.myprept.features.repos.model

import android.graphics.Movie
import com.example.myprept.core.data.source.ReposRepository
import com.example.myprept.core.exception.Failure
import com.example.myprept.core.functional.Either
import com.example.myprept.core.interactor.UseCase
import javax.inject.Inject


class GetReposUseCase
@Inject constructor(private val moviesRepository: ReposRepository) : UseCase<List<Repo>, String>() {
    override suspend fun run(params: String): Either<Failure, List<Repo>> =  moviesRepository.getRepos(params, 1, 15)
}

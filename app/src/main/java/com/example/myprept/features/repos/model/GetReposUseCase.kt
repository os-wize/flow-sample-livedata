package com.example.myprept.features.repos.model

import com.example.myprept.core.data.source.ReposRepository
import com.example.myprept.core.exception.Failure
import com.example.myprept.core.functional.Either
import com.example.myprept.core.interactor.UseCase
import javax.inject.Inject


class GetReposUseCase
@Inject constructor(private val reposRepository: ReposRepository) : UseCase<List<Repo>, String>() {
    override suspend fun run(params: String): Either<Failure, List<Repo>> =  reposRepository.getRepos(params, 1, 15)
}

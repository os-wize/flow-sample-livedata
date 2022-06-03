package com.example.myprept.core.data.source

import com.example.myprept.core.exception.Failure
import com.example.myprept.core.functional.Either
import com.example.myprept.core.model.ReposResponse
import com.example.myprept.features.repos.model.Repo
import com.example.myprept.utils.async.ThreadManager
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject


interface ReposRepository {
     suspend fun getRepos(query: String): Either<Failure, List<Repo>>

    class Network
    @Inject constructor(
        private val service: RepoService,
        private val threadManager: ThreadManager
    ) : ReposRepository {

        override suspend fun getRepos(query: String): Either<Failure, List<Repo>> {
            return withContext(threadManager.io) {
                val response = service.getRepos(query, 1, 15).execute()
                 when (response.isSuccessful) {
                    true -> Either.Right(response.body()?.items!!.map { repoEntity -> repoEntity.toRepo() })
                    false -> Either.Left(Failure.ServerError)
                }
            }
        }
    }
}
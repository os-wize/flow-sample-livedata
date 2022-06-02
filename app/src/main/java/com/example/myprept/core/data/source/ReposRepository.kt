package com.example.myprept.core.data.source

import com.example.myprept.core.exception.Failure
import com.example.myprept.core.functional.Either
import com.example.myprept.core.model.ReposResponse
import com.example.myprept.features.repos.model.Repo
import retrofit2.Call
import javax.inject.Inject


interface ReposRepository {
    fun getRepos(query: String, page: Int, itemsPerPage: Int): Either<Failure, List<Repo>>

    class Network
    @Inject constructor(
        private val service: RepoService
    ) : ReposRepository {

        private fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }

        override fun getRepos(
            query: String,
            page: Int,
            itemsPerPage: Int
        ): Either<Failure, List<Repo>> = request(
            service.getRepos(query, page, itemsPerPage),
            { it.items!!.map { repoEntity -> repoEntity.toRepo() } },
            ReposResponse()
        )
    }
}
package com.example.myprept.core.data.source

import com.example.myprept.core.data.gitrepo.RepoApi
import com.example.myprept.core.model.ReposResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepoService
@Inject constructor(retrofit: Retrofit) : RepoApi {
    private val reposApi by lazy { retrofit.create(RepoApi::class.java) }
    override fun getRepos(query: String, page: Int, itemsPerPage: Int): Call<ReposResponse> = reposApi.getRepos(query, page, itemsPerPage)

}
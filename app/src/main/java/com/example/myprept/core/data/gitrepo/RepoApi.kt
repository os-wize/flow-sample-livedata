package com.example.myprept.core.data.gitrepo

import com.example.myprept.core.model.ReposResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


internal interface RepoApi {
    @GET("search/repositories?sort=stars")
    fun getRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Call<ReposResponse>
}

package com.aradipatrik.rxmigration.api.definitions

import com.aradipatrik.rxmigration.api.models.RepoWire
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{owner}/repos")
    suspend fun getRepos(
        @Path("owner") owner: String
    ): List<RepoWire>
}

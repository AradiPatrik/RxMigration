package com.aradipatrik.rxmigration.api.definitions

import com.aradipatrik.rxmigration.api.models.RepoWire
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{owner}/repos")
    fun getRepos(
        @Path("owner") owner: String
    ): Single<List<RepoWire>>
}

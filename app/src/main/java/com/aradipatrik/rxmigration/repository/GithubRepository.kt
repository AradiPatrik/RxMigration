package com.aradipatrik.rxmigration.repository

import com.aradipatrik.rxmigration.api.definitions.GithubApi
import com.aradipatrik.rxmigration.api.models.RepoWire
import com.aradipatrik.rxmigration.db.RepoDao
import com.aradipatrik.rxmigration.db.RepoEntity
import com.aradipatrik.rxmigration.mapper.mapToDomain
import com.aradipatrik.rxmigration.mapper.mapToEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val repoDao: RepoDao,
) {
    fun queryRepos(owner: String) = githubApi.getRepos(owner)
        .map(List<RepoWire>::mapToEntity)
        .flatMapCompletable {
            Completable.fromAction {
                repoDao.replaceAllSync(it)
            }
        }
        .subscribeOn(Schedulers.io())

    fun getRepos() = repoDao.getAll()
        .map(List<RepoEntity>::mapToDomain)
}
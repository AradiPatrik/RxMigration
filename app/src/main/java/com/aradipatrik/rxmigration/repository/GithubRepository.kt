package com.aradipatrik.rxmigration.repository

import com.aradipatrik.rxmigration.api.definitions.GithubApi
import com.aradipatrik.rxmigration.api.models.RepoWire
import com.aradipatrik.rxmigration.db.RepoDao
import com.aradipatrik.rxmigration.db.RepoEntity
import com.aradipatrik.rxmigration.mapper.mapToDomain
import com.aradipatrik.rxmigration.mapper.mapToEntity
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.rx3.asFlowable
import kotlinx.coroutines.rx3.rxCompletable
import kotlinx.coroutines.rx3.rxSingle
import kotlinx.coroutines.withContext
import logcat.logcat
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val repoDao: RepoDao,
) {
    suspend fun queryRepos(owner: String) = withContext(Dispatchers.IO) {
        val repos = githubApi.getRepos(owner)
        repoDao.replaceAll(repos.map(RepoWire::mapToEntity))
    }

    fun getRepos() = repoDao.getAll()
        .map(List<RepoEntity>::mapToDomain)
        .flowOn(Dispatchers.IO)
}
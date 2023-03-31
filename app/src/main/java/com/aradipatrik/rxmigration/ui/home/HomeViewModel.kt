package com.aradipatrik.rxmigration.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aradipatrik.rxmigration.domain.Repo
import com.aradipatrik.rxmigration.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val selectedRepoIds = MutableStateFlow(emptySet<Int>())

    private val repos = githubRepository.getRepos()

    val repoItems = combine(repos, selectedRepoIds, transform = List<Repo>::toListItem)
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val mutex = Mutex()

    suspend fun queryRepos(owner: String) = githubRepository.queryRepos(owner)

    fun onItemClick(repoListItem: RepoListItem) = viewModelScope.launch {
        mutex.withLock {
            val currentSelections = selectedRepoIds.value
            if (currentSelections.contains(repoListItem.item.id)) {
                selectedRepoIds.value = currentSelections - repoListItem.item.id
            } else {
                selectedRepoIds.value = selectedRepoIds.value + repoListItem.item.id
            }
        }
    }
}
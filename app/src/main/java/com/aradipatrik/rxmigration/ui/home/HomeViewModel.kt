package com.aradipatrik.rxmigration.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aradipatrik.rxmigration.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.rx3.asFlowable
import kotlinx.coroutines.rx3.rxCompletable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubRepository: GithubRepository
): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val repos = githubRepository.getRepos().asFlowable()

    fun queryRepos(owner: String) = rxCompletable { githubRepository.queryRepos(owner) }
}
package com.aradipatrik.rxmigration.ui.home

import com.aradipatrik.rxmigration.domain.Repo

fun Repo.toListItem(isSelected: Boolean) = RepoListItem(this, isSelected)

fun List<Repo>.toListItem(selectedRepoIds: Set<Int>) = map { repo ->
    repo.toListItem(selectedRepoIds.contains(repo.id))
}
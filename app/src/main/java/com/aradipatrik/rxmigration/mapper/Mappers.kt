package com.aradipatrik.rxmigration.mapper

import com.aradipatrik.rxmigration.api.models.RepoWire
import com.aradipatrik.rxmigration.db.RepoEntity
import com.aradipatrik.rxmigration.domain.Repo

fun RepoWire.mapToEntity() = RepoEntity(
    id = id,
    name = name,
    description = description,
    owner = owner.login,
    ownerAvatarUrl = owner.avatar_url,
    stars = stargazers_count,
    forks = forks
)

fun List<RepoWire>.mapToEntity() = map { it.mapToEntity() }

fun RepoEntity.mapToDomain() = Repo(
    id = id,
    name = name,
    description = description,
    owner = owner,
    ownerAvatarUrl = ownerAvatarUrl,
    stars = stars,
    forks = forks
)

fun List<RepoEntity>.mapToDomain() = map { it.mapToDomain() }
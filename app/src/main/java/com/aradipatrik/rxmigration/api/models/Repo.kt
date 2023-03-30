package com.aradipatrik.rxmigration.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoWire (
    val id: Int,
    val name: String,
    val description: String?,
    val forks: Int,
    val stargazers_count: Int,
    val owner: RepoOwnerWire
)

@JsonClass(generateAdapter = true)
data class RepoOwnerWire(
    val id: Int,
    val avatar_url: String,
    val login: String
)
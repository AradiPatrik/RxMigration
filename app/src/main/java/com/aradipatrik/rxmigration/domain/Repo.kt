package com.aradipatrik.rxmigration.domain

data class Repo(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: String,
    val ownerAvatarUrl: String,
    val stars: Int,
    val forks: Int,
)
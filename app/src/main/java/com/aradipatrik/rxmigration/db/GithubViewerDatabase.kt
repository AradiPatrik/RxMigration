package com.aradipatrik.rxmigration.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepoEntity::class], version = 1)
abstract class GithubViewerDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao

    companion object {
        const val DATABASE_NAME: String = "github_viewer_db"
    }
}
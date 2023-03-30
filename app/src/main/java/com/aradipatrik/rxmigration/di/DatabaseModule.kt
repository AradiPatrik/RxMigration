package com.aradipatrik.rxmigration.di

import android.content.Context
import androidx.room.Room
import com.aradipatrik.rxmigration.db.GithubViewerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideGithubViewerDatabase(@ApplicationContext context: Context): GithubViewerDatabase =
        Room.databaseBuilder(
            context,
            GithubViewerDatabase::class.java,
            GithubViewerDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideRepoDao(githubViewerDatabase: GithubViewerDatabase) = githubViewerDatabase.repoDao()
}
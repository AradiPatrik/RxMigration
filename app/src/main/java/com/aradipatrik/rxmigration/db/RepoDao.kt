package com.aradipatrik.rxmigration.db

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)

    @Query("SELECT * FROM repos WHERE id LIKE :id")
    suspend fun get(id: String): RepoEntity

    @Query("SELECT * FROM repos")
    fun getAll(): Flow<List<RepoEntity>>

    @Query("DELETE FROM repos")
    suspend fun clearAll()

    @Transaction
    suspend fun replaceAll(repos: List<RepoEntity>) {
        clearAll()
        insertAll(repos)
    }
}
package com.aradipatrik.rxmigration.db

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repos: List<RepoEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSync(repos: List<RepoEntity>)

    @Query("SELECT * FROM repos WHERE id LIKE :id")
    fun get(id: String): Single<RepoEntity>

    @Query("SELECT * FROM repos")
    fun getAll(): Flowable<List<RepoEntity>>

    @Query("DELETE FROM repos")
    fun clearAll(): Completable

    @Query("DELETE FROM repos")
    fun clearAllSync()

    @Transaction
    fun replaceAllSync(repos: List<RepoEntity>) {
        clearAllSync()
        insertAllSync(repos)
    }


}
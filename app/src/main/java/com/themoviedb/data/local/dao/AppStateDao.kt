package com.themoviedb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.themoviedb.data.local.model.AppStateEntity

@Dao
interface AppStateDao {

    @Query("SELECT lastUpdateTime FROM app_state LIMIT 1")
    suspend fun getLastUpdateTime(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLastUpdateTime(appState: AppStateEntity)

    @Query("DELETE FROM app_state")
    suspend fun clear()
}

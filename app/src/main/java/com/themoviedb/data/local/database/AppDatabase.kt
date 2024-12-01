package com.themoviedb.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.themoviedb.data.local.dao.AppStateDao
import com.themoviedb.data.local.dao.MovieDao
import com.themoviedb.data.local.dao.MovieDetailDao
import com.themoviedb.data.local.model.AppStateEntity
import com.themoviedb.data.local.model.MovieDetailConverters
import com.themoviedb.data.local.model.MovieDetailEntity
import com.themoviedb.data.local.model.MovieEntity

@Database(entities = [MovieEntity::class, MovieDetailEntity::class, AppStateEntity::class], version = 1, exportSchema = false)
@TypeConverters(MovieDetailConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailsDao(): MovieDetailDao
    abstract fun appStateDao(): AppStateDao
}
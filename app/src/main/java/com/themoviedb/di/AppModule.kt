package com.themoviedb.di

import android.content.Context
import androidx.room.Room
import com.themoviedb.data.local.LocalDataSource
import com.themoviedb.data.local.LocalDataSourceImpl
import com.themoviedb.data.local.dao.AppStateDao
import com.themoviedb.data.local.dao.MovieDao
import com.themoviedb.data.local.dao.MovieDetailDao
import com.themoviedb.data.local.database.AppDatabase
import com.themoviedb.data.local.model.HeaderInterceptor
import com.themoviedb.data.remote.api.MovieApiService
import com.themoviedb.data.repository.MovieRepositoryImpl
import com.themoviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(HeaderInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: MovieApiService,
        localDataSource: LocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(apiService, localDataSource)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieDetailDao(database: AppDatabase): MovieDetailDao {
        return database.movieDetailsDao()
    }

    @Provides
    @Singleton
    fun provideAppStateDao(database: AppDatabase): AppStateDao {
        return database.appStateDao()
    }


    @Provides
    @Singleton
    fun provideLocalDataSource(
        movieDao: MovieDao,
        movieDetailsDao: MovieDetailDao,
        appStateDao: AppStateDao
    ): LocalDataSource {
        return LocalDataSourceImpl(movieDao, movieDetailsDao, appStateDao)
    }
}
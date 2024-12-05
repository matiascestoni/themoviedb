package com.themoviedb.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.themoviedb.data.local.dao.AppStateDao
import com.themoviedb.data.local.database.AppDatabase
import com.themoviedb.data.local.model.AppStateEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppStateDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var appStateDao: AppStateDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        appStateDao = database.appStateDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieveLastUpdateTime() = runTest {
        // Arrange
        val testEntity = AppStateEntity(lastUpdateTime = 123456789L)

        // Act
        appStateDao.updateLastUpdateTime(testEntity)

        // Assert
        val result = appStateDao.getLastUpdateTime()
        assertEquals(123456789L, result)
    }

    @Test
    fun testClearAppState() = runTest {
        val testEntity = AppStateEntity(lastUpdateTime = 987654321L)

        // Arrange
        appStateDao.updateLastUpdateTime(testEntity)
        val beforeClear = appStateDao.getLastUpdateTime()
        assertEquals(987654321L, beforeClear)

        // Act
        appStateDao.clear()

        // Assert
        val afterClear = appStateDao.getLastUpdateTime()
        assertNull(afterClear)
    }
}

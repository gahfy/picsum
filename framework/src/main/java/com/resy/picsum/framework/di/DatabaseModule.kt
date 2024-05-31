package com.resy.picsum.framework.di

import android.content.Context
import androidx.room.Room
import com.resy.picsum.framework.database.AppDatabase
import com.resy.picsum.framework.database.dao.CachedFileDao
import com.resy.picsum.framework.database.dao.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The name of the database of the application
 */
private const val DATABASE_NAME = "PicsumDb"

/**
 * Dependency module which provides everything related to Database.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    /**
     * Provides the database of the application.
     *
     * @param context the context in which the application is running
     *
     * @return the database of the application
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    /**
     * Provides the image DAO to be used in the application.
     *
     * @param appDatabase the database used by the application.
     *
     * @return the image DAO to be used in the application
     */
    @Provides
    @Singleton
    fun provideImageDao(appDatabase: AppDatabase): ImageDao {
        return appDatabase.imageDao()
    }

    /**
     * Provides the cached file DAO to be used in the application
     *
     * @param appDatabase the database used by the application
     *
     * @return the cached file DAO to be used in the application
     */
    @Provides
    @Singleton
    fun provideCachedFileDao(appDatabase: AppDatabase): CachedFileDao {
        return appDatabase.cachedFileDao()
    }
}

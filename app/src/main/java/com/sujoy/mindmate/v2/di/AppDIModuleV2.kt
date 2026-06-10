package com.sujoy.mindmate.v2.di

import android.content.Context
import com.sujoy.mindmate.v2.data.database.MindMateDatabaseV2
import com.sujoy.mindmate.v2.data.database.V2DatabaseDAO
import com.sujoy.mindmate.v2.data.repositories.DBRepositoryImplV2
import com.sujoy.mindmate.v2.data.repositories.DatabaseRepositoryV2
import com.sujoy.mindmate.v2.utils.DataStoreManagerV2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDIModuleV2 {

    @Provides
    @Singleton
    fun provideMindMateDatabaseV2(@ApplicationContext context: Context): MindMateDatabaseV2 {
        return MindMateDatabaseV2.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideV2DatabaseDAO(database: MindMateDatabaseV2): V2DatabaseDAO {
        return database.appDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepositoryV2(dao: V2DatabaseDAO): DatabaseRepositoryV2 {
        return DBRepositoryImplV2(dao)
    }

    @Provides
    @Singleton
    fun provideDataStoreManagerV2(@ApplicationContext context: Context): DataStoreManagerV2 {
        return DataStoreManagerV2(context)
    }
}

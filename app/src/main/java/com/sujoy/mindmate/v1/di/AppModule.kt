package com.sujoy.mindmate.v1.di

import android.content.Context
import com.google.mediapipe.tasks.text.textclassifier.TextClassifier
import com.sujoy.mindmate.v1.utils.DataStoreManager
import com.sujoy.mindmate.v1.data.database.AppDAO
import com.sujoy.mindmate.v1.data.database.MindMateDatabase
import com.sujoy.mindmate.v1.data.repositories.DatabaseRepository
import com.sujoy.mindmate.v1.data.repositories.DatabaseRepositoryImpl
import com.sujoy.mindmate.v1.data.repositories.MindMateApiRepoImpl
import com.sujoy.mindmate.v1.data.repositories.MindMateApiRepository
import com.sujoy.mindmate.v1.data.repositories.MlModelRepository
import com.sujoy.mindmate.v1.data.repositories.MlModelRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MindMateDatabase {
        return MindMateDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAppDao(database: MindMateDatabase): AppDAO {
        return database.appDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(appDao: AppDAO): DatabaseRepository {
        return DatabaseRepositoryImpl(appDao)
    }

    @Provides
    @Singleton
    fun provideMindMateApiRepository(): MindMateApiRepository {
        return MindMateApiRepoImpl()
    }

    @Provides
    @Singleton
    fun provideMlModelRepository(
        classifier: TextClassifier
    ): MlModelRepository {
        return MlModelRepositoryImpl(classifier)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}

package com.sujoy.mindmate.di

import android.content.Context
import com.google.mediapipe.tasks.text.textclassifier.TextClassifier
import com.sujoy.mindmate.data.database.AppDAO
import com.sujoy.mindmate.data.database.MindMateDatabase
import com.sujoy.mindmate.data.repositories.DatabaseRepository
import com.sujoy.mindmate.data.repositories.DatabaseRepositoryImpl
import com.sujoy.mindmate.data.repositories.MindMateApiRepoImpl
import com.sujoy.mindmate.data.repositories.MindMateApiRepository
import com.sujoy.mindmate.data.repositories.MlModelRepository
import com.sujoy.mindmate.data.repositories.MlModelRepositoryImpl
import com.sujoy.mindmate.utils.DataStoreManager
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

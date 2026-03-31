package com.sujoy.mindmate.di

import android.content.Context
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.text.textclassifier.TextClassifier
import com.google.mediapipe.tasks.text.textclassifier.TextClassifier.TextClassifierOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MlModule {

    @Provides
    @Singleton
    fun provideTextClassifier(@ApplicationContext context: Context): TextClassifier {
        val baseOptions = BaseOptions.builder()
            .setModelAssetPath("bert_classifier.tflite")
            .build()

        val options = TextClassifierOptions.builder()
            .setBaseOptions(baseOptions)
            .build()

        return TextClassifier.createFromOptions(context, options)
    }
}

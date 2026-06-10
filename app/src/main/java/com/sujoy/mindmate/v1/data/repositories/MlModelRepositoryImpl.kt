package com.sujoy.mindmate.v1.data.repositories

import android.util.Log
import com.google.mediapipe.tasks.text.textclassifier.TextClassifier
import com.sujoy.mindmate.v1.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.v1.data.models.MoodsEnum
import com.sujoy.mindmate.v1.utils.ConstantsManager
import com.sujoy.mindmate.v1.utils.UtilityMethods
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MlModelRepositoryImpl @Inject constructor(
    private val classifier: TextClassifier
) : MlModelRepository {
    override suspend fun analyzeSentimentLocal(
        entryText: String,
        journalID: String
    ): Result<JournalAnalyzedDbModel> {
        return withContext(Dispatchers.Default) {
            try {
                val classificationResult = classifier.classify(entryText)

                // Get the top category from the classifications
                val topCategory = classificationResult.classificationResult()
                    .classifications()
                    .firstOrNull()
                    ?.categories()
                    ?.maxByOrNull { it.score() }

                if (topCategory == null) {
                    return@withContext Result.failure(Exception("No classification results"))
                }

                val topLabel = topCategory.categoryName().lowercase()
                val sentimentScore = topCategory.score()

                val analysisId = UtilityMethods.generateUniqueAnalysisId()

                // currently we only classify positive as Happy and negative as Sad due to limited number of labels from model.
                val mood = when {
                    topLabel.contains("positive") -> MoodsEnum.HAPPY.name
                    topLabel.contains("negative") -> MoodsEnum.SAD.name
                    else -> MoodsEnum.NEUTRAL.name
                }

                val message = when (mood) {
                    MoodsEnum.HAPPY.name -> "I'm so glad you're having a wonderful day! Keep that positive energy going."
                    MoodsEnum.SAD.name -> "It's okay to feel down sometimes. Remember that I'm here for you, and better days are ahead."
                    else -> "Thank you for sharing your thoughts with me. I'm listening."
                }

                Result.success(
                    JournalAnalyzedDbModel(
                        id = analysisId,
                        journalId = journalID,
                        sentimentScore = sentimentScore,
                        mood = mood,
                        message = message,
                        timeStamp = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                Log.e(ConstantsManager.APP_TAG, "analyzeSentimentLocal error: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}

package com.sujoy.mindmate.repositories

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.ai.FirebaseAI
import com.sujoy.mindmate.models.AnalyzedMoodObject
import com.sujoy.mindmate.models.Moods
import com.sujoy.mindmate.utils.ConstantsManager
import org.json.JSONObject

class MindMateAppRepoImpl : MindMateAppRepository {
    private val model = FirebaseAI.getInstance(FirebaseApp.getInstance()).generativeModel(
        ConstantsManager.GEN_MODEL_VERSION
    )

    override suspend fun analyzeMood(entryText: String): Result<AnalyzedMoodObject> {
        // 1. Get all enum names as a list of strings and join them separated by a comma
        val moodOptions = Moods.entries.joinToString(", ") { it.name }


        val prompt = """
            You are a mood analysis assistant for a journaling app called MindMate.
            Read the user's journal entry and respond with:
            - The detected mood (choose one: ${moodOptions})
            - A one-line supportive message based on that mood.
            Respond strictly in JSON format like this:
            {
              "mood": "<mood>",
              "message": "<message>"
            }

            Journal Entry: "$entryText"
        """.trimIndent()

        return try {
            val response = model.generateContent(prompt)
            if (response.text.isNullOrEmpty()) {
                return Result.failure(Exception("No response text"))
            } else {
                val cleanedJson =
                    response.text!!.removePrefix("```json").removePrefix("{").removeSuffix("}")
                        .trim()

                val jsonObject = JSONObject(cleanedJson)
                val mood = jsonObject.getString("mood")
                val message = jsonObject.getString("message")

                Result.success(AnalyzedMoodObject(mood, message))
            }
        } catch (e: Exception) {
            // This will catch potential network errors or JSON parsing errors.
            Log.e(ConstantsManager.Error_Tag, "analyzeMood: ${e.message}")
            Result.failure(e)
        }
    }
}
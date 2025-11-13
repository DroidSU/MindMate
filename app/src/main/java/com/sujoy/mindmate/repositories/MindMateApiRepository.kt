package com.sujoy.mindmate.repositories

import com.google.firebase.auth.FirebaseUser
import com.sujoy.mindmate.models.AnalyzedMoodObject

interface MindMateApiRepository {
    val currentUser: FirebaseUser?

    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
    suspend fun signUpWithEmail(email: String, password: String): Result<Unit>

    suspend fun analyzeMood(entryText: String): Result<AnalyzedMoodObject>
}
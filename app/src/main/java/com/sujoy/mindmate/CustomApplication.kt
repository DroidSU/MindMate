package com.sujoy.mindmate

import android.app.Application
import com.google.firebase.FirebaseApp
import com.sujoy.mindmate.db.MindMateDatabase

class CustomApplication : Application() {

    // Lazy initialization of the database.
    // The database will be created only when it's first accessed.
    val database: MindMateDatabase by lazy { MindMateDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}

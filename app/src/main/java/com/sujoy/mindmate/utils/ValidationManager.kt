package com.sujoy.mindmate.utils

import android.util.Patterns

class ValidationManager {

    fun validateEmail(email: String): Int {
        // 0 - Pass
        // 1 - Email cannot be empty
        // 2 - Invalid email format
        if (email.isEmpty()) {
            return 1
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return 2
        }
        return 0
    }

    fun validatePassword(password : String, confirmPassword : String) : Int {
        // 0 - Pass
        // 1 - Password cannot be empty
        // 2 - Password must be at least 6 characters
        // 3 - Password is not equal to Confirm Password
        if(password.isEmpty()){
            return 1
        }
        if (password.length < 6) {
            return 2
        }
        if(password != confirmPassword){
            return 3
        }
        return 0
    }
}
package com.zennjero.kook.app.presentation.auth

import androidx.lifecycle.ViewModel
import com.zennjero.kook.app.presentation.util.Constant.KOOK
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

class LoginViewModel : ViewModel() {
    var storedVerificationId: String = ""
    var phoneNumber = ""
    var otp = ""
    var loginType = KOOK

    fun sendOTP(options: PhoneAuthOptions) {
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}
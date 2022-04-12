package com.zennjero.kook.app.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zennjero.kook.app.R
import com.zennjero.kook.app.presentation.util.replaceFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        replaceFragment(R.id.login_frame, MobileFragment())
    }
}
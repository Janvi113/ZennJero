package com.zennjero.kook.app.presentation.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.ActivityRegistrationBinding
import com.zennjero.kook.app.presentation.personaldetails.PersonalDetailsFragment
import com.zennjero.kook.app.presentation.util.Constant.MOBILE_NUMBER_TAG
import com.zennjero.kook.app.presentation.util.replaceFragment

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)

        val intent = intent
        val fragment = intent.getStringExtra(MOBILE_NUMBER_TAG)
            ?.let { PersonalDetailsFragment.newInstance(it) }
        fragment?.let { replaceFragment(R.id.frame, it) }
    }
}
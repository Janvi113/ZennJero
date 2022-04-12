package com.zennjero.kook.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.ActivityMainBinding
import com.zennjero.kook.app.presentation.util.findNavHostFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpBottomNavigationView()
    }

    private fun setUpBottomNavigationView() {
        binding.bottomNavigationView.labelVisibilityMode =
            NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavigationView.setupWithNavController(findNavHostFragment(R.id.navHostFragment).navController)
    }

    override fun onBackPressed(){
        finishAffinity()
    }
}
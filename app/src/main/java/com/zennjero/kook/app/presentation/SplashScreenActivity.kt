package com.zennjero.kook.app.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.ActivitySplashScreenBinding
import com.zennjero.kook.app.presentation.auth.RoleSelectionActivity
import com.zennjero.kook.app.presentation.util.Constant

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val TAG = SplashScreenActivity::class.java.simpleName
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        makeActivityFullScreen()

        // This function sets the animated vector drawable to start state
        resetAnimation()

        // start the animation
        startAnimation()

        setUpActivityFlow()
    }

    private fun setUpActivityFlow() {
        Handler(Looper.getMainLooper()).postDelayed({
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                // new user
                startActivity(Intent(this, RoleSelectionActivity::class.java))
                finish()
            } else {
                // already logged in user
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, Constant.SPLASH_SCREEN_ANIMATION_DURATION)
    }

    private fun startAnimation() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.deliveryBoyAnimationView.drawable?.let {
                if (it is AnimatedVectorDrawable) {
                    it.start()
                    Log.d(TAG, "Animation Started")
                }
            }
        }, Constant.SPLASH_SCREEN_ANIMATION_START_OFFSET)
    }

    private fun resetAnimation() {
        binding.deliveryBoyAnimationView.drawable?.let {
            if (it is AnimatedVectorDrawable) {
                it.reset()
            }
        }
    }

    private fun makeActivityFullScreen() {
        window.statusBarColor = resources.getColor(R.color.backgroundColor, theme)

        WindowInsetsControllerCompat(
            window,
            window.decorView.findViewById(android.R.id.content)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())

            // When the screen is swiped up at the bottom
            // of the application, the navigationBar shall
            // appear for some time
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        Log.d(TAG, "Activity is fullscreen now")
    }
}
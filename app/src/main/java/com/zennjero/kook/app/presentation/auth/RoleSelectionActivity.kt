package com.zennjero.kook.app.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.ActivityRoleSelectionBinding
import com.zennjero.kook.app.presentation.util.Constant.BUYER
import com.zennjero.kook.app.presentation.util.Constant.KOOK
import com.zennjero.kook.app.presentation.util.Constant.LOGIN_TYPE

class RoleSelectionActivity : AppCompatActivity() {

    lateinit var binding: ActivityRoleSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this@RoleSelectionActivity,
            R.layout.activity_role_selection
        )

        val intent = Intent(this@RoleSelectionActivity, LoginActivity::class.java)
        binding.kookLoginbtn.setOnClickListener {
            intent.putExtra(LOGIN_TYPE, KOOK)
            startActivity(intent)
        }
        binding.buyerLoginbtn.setOnClickListener {
            intent.putExtra(LOGIN_TYPE, BUYER)
            startActivity(intent)
        }
    }
}


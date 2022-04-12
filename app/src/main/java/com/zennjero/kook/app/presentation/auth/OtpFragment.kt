package com.zennjero.kook.app.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentOtpBinding
import com.zennjero.kook.app.presentation.user.RegistrationActivity
import com.zennjero.kook.app.presentation.util.Constant.LOGGER
import com.zennjero.kook.app.presentation.util.Constant.LOGIN_TYPE
import com.zennjero.kook.app.presentation.util.Constant.MOBILE_NUMBER_TAG

class OtpFragment : Fragment() {

    val auth = Firebase.auth
    lateinit var viewModel: LoginViewModel
    lateinit var binding: FragmentOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        binding.viewModel = viewModel

        binding.loginBtn.setOnClickListener {
            verifyOtp()
        }

        return binding.root
    }

    private fun verifyOtp() {
        if (viewModel.otp.length != 6) {
            Toast.makeText(activity, R.string.enter_valid_otp, Toast.LENGTH_SHORT).show()
        } else {
            binding.loginBtn.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            verifyPhoneNumberWithCode()
        }
    }

    private fun verifyPhoneNumberWithCode() {
        val credential =
            PhoneAuthProvider.getCredential(viewModel.storedVerificationId!!, viewModel.otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    requireActivity(),
                    R.string.signed_in_successfully,
                    Toast.LENGTH_SHORT
                )
                    .show()
                val user = task.result?.user
                if (user != null) {
                    val intent = Intent(requireActivity(), RegistrationActivity::class.java)
                    intent.putExtra(LOGGER, requireActivity().intent.getStringExtra(LOGIN_TYPE))
                    intent.putExtra(MOBILE_NUMBER_TAG, viewModel.phoneNumber)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(requireActivity(), R.string.sign_in_failed, Toast.LENGTH_SHORT)
                    .show()
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireActivity(), R.string.invalid_otp, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}

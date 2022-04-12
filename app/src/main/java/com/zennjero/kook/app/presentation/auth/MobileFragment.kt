package com.zennjero.kook.app.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentMobileBinding
import com.zennjero.kook.app.presentation.util.Constant.LOGIN_TYPE
import com.zennjero.kook.app.presentation.util.Constant.TIMEOUT_TIMER
import com.zennjero.kook.app.presentation.util.addFragment
import com.zennjero.kook.app.presentation.util.replaceFragment
import java.util.concurrent.TimeUnit

class MobileFragment : Fragment() {

    lateinit var binding: FragmentMobileBinding
    lateinit var viewModel: LoginViewModel
    val auth: FirebaseAuth = Firebase.auth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_mobile,
            container,
            false
        )

        binding.viewModel = viewModel

        viewModel.loginType = requireActivity().intent.getStringExtra(LOGIN_TYPE).toString()

        binding.btnGetOtp.setOnClickListener {
            getOtp()
        }
        return binding.root
    }

    private fun getOtp() {
        if (binding.phoneNumEt.text.length != 10) {
            Toast.makeText(activity, R.string.invalid_phone_number, Toast.LENGTH_SHORT).show()
        } else {
            binding.btnGetOtp.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            val options: PhoneAuthOptions = buildOption()
            viewModel.sendOTP(options)
            Toast.makeText(requireActivity(), R.string.otp_send, Toast.LENGTH_SHORT).show()
            activity?.addFragment(R.id.login_frame, OtpFragment(), true)
            activity?.replaceFragment(R.id.login_frame, OtpFragment())
        }
    }

    private fun buildOption(): PhoneAuthOptions {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //This needs to be called for auto verification
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d(TAG, e.toString())
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                viewModel.storedVerificationId = verificationId
            }
        }
        return PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91${viewModel.phoneNumber}")       // Phone number to verify
            .setTimeout(TIMEOUT_TIMER, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
    }

    private val TAG = LoginActivity::class.java.simpleName


}
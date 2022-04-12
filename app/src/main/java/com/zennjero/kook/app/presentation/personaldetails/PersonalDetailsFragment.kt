package com.zennjero.kook.app.presentation.personaldetails

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentPersonalDetailsBinding
import com.zennjero.kook.app.presentation.address.AddressDetailsFragment
import com.zennjero.kook.app.presentation.util.*
import com.zennjero.kook.app.presentation.util.Constant.MIN_AGE_IN_MILLIS
import com.zennjero.kook.app.presentation.util.Constant.MOBILE_NUMBER_TAG
import com.zennjero.kook.app.presentation.util.Utils.Companion.returnDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class PersonalDetailsFragment : Fragment() {

    companion object {
        fun newInstance(number: String): PersonalDetailsFragment {
            val fragment = PersonalDetailsFragment()

            val bundle = Bundle().apply {
                putString(MOBILE_NUMBER_TAG, number)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    //Data Binding
    private lateinit var binding: FragmentPersonalDetailsBinding

    // View Model
    private lateinit var viewModel: PersonalDetailsViewModel

    // list of all required fields
    private val requiredFields: List<TextInputLayout> by lazy {
        listOf(
            binding.nameLayout,
            binding.dobLayout,
            binding.emailLayout
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_personal_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[PersonalDetailsViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.phonenumber.value = arguments?.getString(MOBILE_NUMBER_TAG)
        setDate()
        setListeners()
    }

    private fun setDate() {
        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.dob.setText(returnDate(myCalender))
        }
        binding.dob.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                datePicker,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - MIN_AGE_IN_MILLIS
            datePickerDialog.show()
        }
    }

    private fun setListeners() {
        binding.proceedButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (!Utils.isAnyFieldEmpty(requiredFields) && Utils.isEmailValid(binding.emailLayout)) {
                    binding.progressLayout.show()
                    viewModel.savePersonalDetails()
                        .observe(requireActivity()) {
                            binding.progressLayout.hide()
                            activity?.addFragment(R.id.frame, AddressDetailsFragment(), true)
                        }
                }
            }
        }

        // set all the required fields
        requiredFields.forEach {
            it.setRequired()
        }
    }
}
package com.zennjero.kook.app.presentation.address

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zennjero.kook.app.presentation.MainActivity
import com.zennjero.kook.app.domain.Location
import com.zennjero.kook.app.framework.network.Status
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentAddressDetailsBinding
import com.zennjero.kook.app.presentation.util.*

class AddressDetailsFragment : Fragment() {

    // Data Binding
    private lateinit var binding: FragmentAddressDetailsBinding

    // View Model
    private lateinit var viewModel: AddressViewModel

    // Activity Result launcher for launching map activity
    private lateinit var mapLauncher: ActivityResultLauncher<Intent>

    // This permission launcher will be used for requesting location permission
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    // list of all required fields
    private val requiredFields: List<TextInputLayout> by lazy {
        listOf(
            binding.addressNameLayout,
            binding.landmarkLayout,
            binding.houseFlatNoLayout,
            binding.apartmentNameLayout,
            binding.addressLineLayout
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // initializing the map launcher
        mapLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                activityResult.data?.let { data ->
                    // save data in view model
                    viewModel.addressLine = data.getStringExtra(MapActivity.DATA_ADDRESS_LINE)
                    viewModel.location = Location(
                        latitude = data.getDoubleExtra(
                            MapActivity.DATA_LATITUDE,
                            Constant.DEFAULT_LATITUDE
                        ),
                        longitude = data.getDoubleExtra(
                            MapActivity.DATA_LONGITUDE,
                            Constant.DEFAULT_LONGITUDE
                        )
                    )

                    // set address in the field and remove error
                    binding.addressLine.setText(viewModel.addressLine)
                    binding.addressLine.error = null
                }
            }
        }

        // initializing the permission launcher
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            if (result[Manifest.permission.ACCESS_FINE_LOCATION] == true && result[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                mapLauncher.launch(Intent(requireContext(), MapActivity::class.java))
                viewModel.deniedLocationPermission.postValue(false)
            } else {
                viewModel.deniedLocationPermission.postValue(true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_address_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the view model
        viewModel = ViewModelProvider(requireActivity())[AddressViewModel::class.java]
        binding.viewModel = viewModel

        binding.addressLine.setOnClickListener {
            launchMap()
        }

        binding.requiredPermissionLayout.allowButton.setOnClickListener {
            launchMap()
        }

        binding.proceedButton.setOnClickListener {
            if (!Utils.isAnyFieldEmpty(requiredFields)) {
                // All fields are completed
                // start uploading address
                binding.progressLayout.show()
                viewModel.uploadAddress()
                    .observe(requireActivity()) {
                        if (it.status != Status.LOADING) binding.progressLayout.hide()
                        println(it)
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                    }
            }
        }

        viewModel.deniedLocationPermission.observe(requireActivity()) {
            // If the user has denied for the permission then we will show
            // location permission required layout
            binding.requiredPermissionLayout.root.visibility = if (it) View.VISIBLE else View.GONE
        }


        // set all the required fields
        requiredFields.forEach {
            it.setRequired()
        }

        // Enable fcm token generation
        // Initially it was disabled because we have to
        // save token by user id and at the start it is null
        // but now we have the user id so enable it.
        Firebase.messaging.isAutoInitEnabled = true
    }

    private fun launchMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // show the map
            mapLauncher.launch(Intent(requireContext(), MapActivity::class.java))
        } else {
            // request location permission
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

}
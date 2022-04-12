package com.zennjero.kook.app.presentation.address

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.zennjero.kook.app.R
import com.zennjero.kook.app.databinding.FragmentPickLocationBinding
import com.zennjero.kook.app.domain.Location
import com.zennjero.kook.app.presentation.util.Constant
import com.zennjero.kook.app.presentation.util.hide
import com.zennjero.kook.app.presentation.util.show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PickLocationFragment : Fragment() {

    // Data Binding
    private lateinit var binding: FragmentPickLocationBinding

    // View Model
    private lateinit var viewModel: MapViewModel

    // It is used for getting users last known location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Keep track of location clicked
    private var location: LatLng = LatLng(Constant.DEFAULT_LATITUDE, Constant.DEFAULT_LONGITUDE)

    // This permission launcher will be used for requesting location permission
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    // holds instance of google map
    private lateinit var map: GoogleMap

    private val locationProviderChangeBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action == LocationManager.EXTRA_LOCATION_ENABLED && isAnyLocationProviderOn()) {
                showUsersLocation()
                println("Enabled")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private val intentFilter = IntentFilter(LocationManager.EXTRA_LOCATION_ENABLED)
    /**
     * This callback is used by get map async while initializing
     * the map
     */
    private val callback = OnMapReadyCallback { mMap ->
        map = mMap
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // show users current location on the map
            if(isAnyLocationProviderOn()){
                showUsersLocation()
            }else{
                showTurnOnLocationAlert()
            }
        } else {
            // request location permission
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        // setting up a map click listener
        // this used for placing the marker where ever the user clicks
        // on the map
        mMap.setOnMapClickListener { latLng1: LatLng? ->
            if (latLng1 == null) return@setOnMapClickListener
            // first we will remove all the placed markers from the map
            mMap.clear()
            // place marker at the clicked location
            mMap.addMarker(MarkerOptions().position(latLng1))

            // update the location
            location = latLng1
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // initializing the permission launcher
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            if (result[Manifest.permission.ACCESS_FINE_LOCATION] == true && result[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                showUsersLocation()
            } else {
                showDefaultLocation()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pick_location, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the view model
        viewModel = ViewModelProvider(requireActivity())[MapViewModel::class.java]

        // Set the listener to pick button
        binding.pick.setOnClickListener {
            binding.progressLayout.show()
            CoroutineScope(Dispatchers.IO).launch {
                val address = decodeAddress(
                    lat = location.latitude,
                    lng = location.longitude
                )

                viewModel.addressLine = address
                viewModel.location =
                    Location(latitude = location.latitude, longitude = location.longitude)
                viewModel.resultAvailable.postValue(true)

                requireActivity().runOnUiThread {
                    binding.progressLayout.hide()
                }
            }
        }

        // Initializing location provider client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Initializing the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().registerReceiver(locationProviderChangeBroadcastReceiver, intentFilter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().unregisterReceiver(locationProviderChangeBroadcastReceiver)
        }
    }
    /**
     * This method will set some default location
     * on the map, in case if the location permission
     * is denied
     */
    private fun showDefaultLocation() {
        val latLng = LatLng(28.38, 77.12)
        map.addMarker(MarkerOptions().position(latLng))
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        this.location = latLng
    }

    /**
     * This method will set users current location
     * on the map
     */
    @SuppressLint("MissingPermission")
    private fun showUsersLocation() {
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation
            .addOnSuccessListener(requireActivity()) { location ->
                if (location == null) return@addOnSuccessListener
                val latLng = LatLng(location.latitude, location.longitude)
                map.addMarker(MarkerOptions().position(latLng))
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                map.moveCamera(CameraUpdateFactory.zoomTo(15f))
                this.location = latLng
            }
    }

    /**
     * This method will decode the latitude longitude and return
     * the address line for the location
     * and if some error occurs returns null
     */
    private fun decodeAddress(lat: Double, lng: Double): String? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return try {
            val addresses =
                geocoder.getFromLocation(lat, lng, 1)
            val address = addresses[0]
            address.getAddressLine(0)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * This method simply check that any location provider
     * either GPS or Network Provider is on, and returns
     * true is any one or both enabled
     */
    fun isAnyLocationProviderOn():Boolean{
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val networkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        return gpsEnabled || networkProviderEnabled
    }

    private fun showTurnOnLocationAlert() {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage(Constant.LOCATION_PROVIDER_OFF_ERROR)
            .setPositiveButton("Settings"){ _, _ ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
            .create()
        alertDialog.show()
    }
}
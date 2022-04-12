package com.zennjero.kook.app.presentation.address

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zennjero.kook.app.R
import com.zennjero.kook.app.presentation.util.replaceFragment

class MapActivity : AppCompatActivity() {

    companion object {
        const val DATA_ADDRESS_LINE = "addressLine"
        const val DATA_LATITUDE = "lat"
        const val DATA_LONGITUDE = "lng"
    }

    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        replaceFragment(R.id.frame, PickLocationFragment())

        viewModel = ViewModelProvider(this)[MapViewModel::class.java]

        viewModel.resultAvailable.observe(this) {
            if (it == true) {
                val result = Intent().apply {
                    putExtra(DATA_ADDRESS_LINE, viewModel.addressLine)
                    putExtra(DATA_LATITUDE, viewModel.location?.latitude)
                    putExtra(DATA_LONGITUDE, viewModel.location?.longitude)
                }
                setResult(RESULT_OK, result)
                finish()
            }
        }
    }
}
package org.triniti.greensmart.ui.home.bins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_f_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.databinding.LayoutFHomeBinding
import org.triniti.greensmart.ui.auth.AuthViewModel
import org.triniti.greensmart.ui.auth.AuthViewModelFactory
import org.triniti.greensmart.ui.home.about.AboutViewModel
import org.triniti.greensmart.ui.home.about.AboutViewModelFactory
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.convertBinToLatLng
import org.triniti.greensmart.utilities.getLocation
import org.triniti.greensmart.utilities.setUpFragmentMap


class Bins : Fragment(), OnMapReadyCallback, KodeinAware, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, OnCompletionListener {

    override fun onCompletion(user: User) {
        authViewModel.saveUser(user)
    }

    private var mMap: GoogleMap? = null
    override val kodein by kodein()
    private val binsFactory: BinsViewModelFactory by instance()
    private val userFactory: AuthViewModelFactory by instance()
    private val aboutFactory: AboutViewModelFactory by instance()
    private lateinit var binsViewModel: BinsViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var aboutViewModel: AboutViewModel
    private var googleApiClient: GoogleApiClient? = null

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.setOnCameraMoveStartedListener { reasonCode ->
            if (reasonCode == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {

            }
        }

        val listener = object : GetLatLng {
            override fun onSuccess(latLng: LatLng) {
                val camPos = CameraPosition.Builder()
                    .target(latLng)
                    .zoom(12f)
                    .tilt(70f)
                    .build()
                val camUpd3 = CameraUpdateFactory.newCameraPosition(camPos)
                googleMap.addMarker(MarkerOptions().position(latLng).title("You are here"))
                googleMap.animateCamera(camUpd3)
            }

            override fun onFailure(message: String?) {
            }
        }

        requireActivity().getLocation(listener)

        Coroutines.main {
            binsViewModel.bins.await().observe(this, Observer {
                it.convertBinToLatLng(googleMap, context!!)
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binsViewModel = ViewModelProviders.of(this, binsFactory).get(BinsViewModel::class.java)
        authViewModel = ViewModelProviders.of(this, userFactory).get(AuthViewModel::class.java)
        aboutViewModel = ViewModelProviders.of(this, aboutFactory).get(AboutViewModel::class.java)

        val binding: LayoutFHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_f_home, container, false)
        binding.viewModel = binsViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), 231
        )

        setListeners()

        authViewModel.getLoggedInUser().observe(this, Observer {
            if (it != null) {
                val cardId = it.cardId

                if (cardId != null) {
                    if (cardId.isEmpty() || cardId.isBlank())
                        Complete().show(childFragmentManager, "Completion")
                }
            }
        })

        setUpFragmentMap(R.id.fragMaps)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        googleApiClient = GoogleApiClient.Builder(context!!)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {
    }

    private fun setListeners() {
        flStats.setOnClickListener {
            findNavController().navigate(R.id.destination_points)
        }

        imMore.setOnClickListener {
            findNavController().navigate(R.id.destination_settings)
        }
        imShop.setOnClickListener {
            findNavController().navigate(R.id.destination_mall)
        }
        imStats.setOnClickListener {
            findNavController().navigate(R.id.destination_points)
        }
    }
}
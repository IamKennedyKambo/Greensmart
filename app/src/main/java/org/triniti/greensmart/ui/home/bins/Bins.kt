package org.triniti.greensmart.ui.home.bins

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.elconfidencial.bubbleshowcase.BubbleShowCase
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import com.elconfidencial.bubbleshowcase.BubbleShowCaseListener
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
import org.triniti.greensmart.databinding.LayoutFHomeBinding
import org.triniti.greensmart.ui.auth.AuthViewModel
import org.triniti.greensmart.ui.auth.AuthViewModelFactory
import org.triniti.greensmart.ui.home.about.AboutViewModel
import org.triniti.greensmart.ui.home.about.AboutViewModelFactory
import org.triniti.greensmart.ui.home.complete.Complete
import org.triniti.greensmart.utilities.*
import org.triniti.greensmart.utilities.Constants.LOCATION_REQUEST_CODE
import org.triniti.greensmart.utilities.Constants.STATISTICS_SHOWN

class Bins : Fragment(), OnMapReadyCallback, KodeinAware, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    private var mMap: GoogleMap? = null
    override val kodein by kodein()
    private val binsFactory: BinsViewModelFactory by instance()
    private val userFactory: AuthViewModelFactory by instance()
    private val aboutFactory: AboutViewModelFactory by instance()
    private lateinit var binsViewModel: BinsViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var aboutViewModel: AboutViewModel
    private var googleApiClient: GoogleApiClient? = null
    private val dataViewModel: DataViewModel by instance()

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val listener = object : OnLatLangListener {
            override fun onFailure(message: String?) {

            }

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

        }

        requireActivity().getLocation(listener)

        Coroutines.main {
            binsViewModel.bins.await().observe(this, Observer {
                if (it != null) {
                    convertBinToLatLng(it, googleMap, context!!)
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binsViewModel = activity?.getViewModel(binsFactory)!!
        authViewModel = activity?.getViewModel(userFactory)!!
        aboutViewModel = activity?.getViewModel(aboutFactory)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), LOCATION_REQUEST_CODE
        )

        setListeners()

        dataViewModel.complete.observe(viewLifecycleOwner, Observer {
            if (it) {
                showTutorial()
                dataViewModel.updateComplete(false)
            }
        })

        aboutViewModel.user.observe(viewLifecycleOwner, Observer {
            tvUsable.text = "${it.usable_points} Points"
//            tvAvailable.text = it.available_points.toString()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (permissions.contentEquals(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                authViewModel.getLoggedInUser().observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        val cardId = it.cardId

                        if (cardId!!.isEmpty() || cardId.isBlank()) {
                            Complete().show(childFragmentManager, "Completion")
                        } else {
                            showTutorial()
                        }
                    }
                })
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {
    }

    private fun setListeners() {
        flStats.setOnClickListener {
            findNavController().navigate(R.id.destination_loyalty)
        }

        imMore.setOnClickListener {
            findNavController().navigate(R.id.destination_about)
        }

        imShop.setOnClickListener {
            findNavController().navigate(R.id.destination_mall)
        }
    }

    private fun showTutorial() {
        activity?.run {
            BubbleShowCaseBuilder(this) //Activity instance
                .title("Care for a tutorial?") //Any title for the bubble view
                .description("Click here to view your account's statistics and news from Greensmart.")
                .showOnce(STATISTICS_SHOWN)
                .targetView(flStats) //View to point out
                .listener(object : BubbleShowCaseListener{
                    override fun onBackgroundDimClick(bubbleShowCase: BubbleShowCase) {

                    }

                    override fun onBubbleClick(bubbleShowCase: BubbleShowCase) {

                    }

                    override fun onCloseActionImageClick(bubbleShowCase: BubbleShowCase) {
                    }

                    override fun onTargetClick(bubbleShowCase: BubbleShowCase) {
                        findNavController().navigate(R.id.destination_loyalty)
                    }

                })
                .show()
        }
    }
}

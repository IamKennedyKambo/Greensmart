package org.triniti.greensmart.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_f_home.*
import org.triniti.greensmart.data.pojos.Bin
import kotlin.math.ln
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor
import org.triniti.greensmart.R


class Home : Fragment(), OnMapReadyCallback {
    override fun onMapReady(p0: GoogleMap?) {
        p0?.setOnCameraMoveStartedListener { reasonCode ->
            if (reasonCode == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {

            }
        }

//        val latLng = LatLng(-1.300, 36.767)
////
////        // Showing the current location in Google Map
////        val camPos = CameraPosition.Builder()
////            .target(latLng)
////            .zoom(19f)
////            .tilt(70f)
////            .build()
////        val camUpd3 = CameraUpdateFactory.newCameraPosition(camPos)
////        p0?.addMarker(MarkerOptions().position(latLng).title("Jamhuri park"))
////        p0?.animateCamera(camUpd3)

        val bins = mutableListOf<Bin>()
        bins.add(Bin(1, -1.354971, 36.657956))
        bins.add(Bin(1, -1.354982, 36.657093))
        bins.add(Bin(1, -1.356044, 36.657055))

        convertbinToLatLng(bins, p0!!)
    }

    private fun zoomMapToRadius(
        latitude: Double,
        longitude: Double,
        radius: Double,
        googleMap: GoogleMap
    ) {
        val position = LatLng(latitude, longitude)
        val center = CameraUpdateFactory.newLatLng(position)
        googleMap.moveCamera(center)
        val zoom = CameraUpdateFactory.zoomTo(getZoomLevel(radius))
        googleMap.animateCamera(zoom)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_f_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        activity?.window?.decorView?.apply {
//            systemUiVisibility =
//                SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//        }

        val fm = childFragmentManager
        val supportMapFragment = SupportMapFragment.newInstance()
        fm.beginTransaction().replace(R.id.fragMaps, supportMapFragment)
            .commit()
        supportMapFragment.getMapAsync(this)

        findNavController().popBackStack()

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

    private fun getZoomLevel(radius: Double): Float {
        return if (radius > 0) {
            val metrics = resources.displayMetrics
            val size = if (metrics.widthPixels < metrics.heightPixels) metrics.widthPixels
            else metrics.heightPixels
            val scale = radius * size / 300000
            (16 - ln(scale) / ln(2.0)).toFloat()
        } else 16f
    }

    private fun convertbinToLatLng(list: List<Bin>, map: GoogleMap) {
        var latLng: LatLng
        val listLats: MutableList<LatLng> = mutableListOf()
        list.forEach { bin ->
            latLng = LatLng(bin.lat, bin.lang)
            listLats.add(latLng)
        }

        addMarkers(listLats, map)
    }

    private fun addMarkers(locations: List<LatLng>, map: GoogleMap) {
        locations.forEach {
            map.addMarker(
                MarkerOptions().position(it).icon(
                    bitmapDescriptorFromVector(
                        requireContext(),
                        R.drawable.vector_bin
                    )
                )
            )
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
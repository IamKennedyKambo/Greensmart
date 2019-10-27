package org.triniti.greensmart.utilities

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Bin
import org.triniti.greensmart.ui.home.bins.GetLatLng
import kotlin.math.ln

fun NavController.navigateUpOrFinish(activity: AppCompatActivity): Boolean {
    return if (navigateUp()) {
        true
    } else {
        activity.finish()
        true
    }
}

fun calculateNoOfColumns(
    context: Context,
    columnWidthDp: Float
): Int { // For example columnWidthdp=180
    val displayMetrics = context.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    return (screenWidthDp / columnWidthDp + 0.5).toInt()
}

fun Fragment.addMenu(toolbar: Toolbar) {
    toolbar.inflateMenu(R.menu.menu_cart)
    toolbar.setOnMenuItemClickListener {
        findNavController().navigate(R.id.destination_cart)
        true
    }
}

fun View.showSnackBar(text: CharSequence, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, text, length).show()
}

fun Context.showToast(text: CharSequence, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun View.toggleVisibility() {
    if (this.isVisible) {
        this.visibility == View.GONE
    } else {
        this.visibility == View.VISIBLE
    }
}

fun Fragment.setUpFragmentMap(resId: Int) {
    if (this is OnMapReadyCallback) {
        val fm = childFragmentManager
        val supportMapFragment = SupportMapFragment.newInstance()
        fm.beginTransaction().replace(resId, supportMapFragment)
            .commit()
        supportMapFragment.getMapAsync(this)
    } else {
        throw MapCallBackException("You should implement OnMapReadCallBack")
    }
}

fun Activity.getLocation(getLatLng: GetLatLng) {
    val mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(this)

    mFusedLocationClient.lastLocation
        .addOnSuccessListener {
            getLatLng.onSuccess(LatLng(it.latitude, it.longitude))
        }
        .addOnFailureListener { e ->
            e.printStackTrace()
            getLatLng.onFailure(e.message)
        }
}

fun Context.zoomMapToRadius(
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

fun Context.getZoomLevel(radius: Double): Float {
    return if (radius > 0) {
        val metrics = resources.displayMetrics
        val size = if (metrics.widthPixels < metrics.heightPixels) metrics.widthPixels
        else metrics.heightPixels
        val scale = radius * size / 300000
        (16 - ln(scale) / ln(2.0)).toFloat()
    } else 16f
}

fun List<Bin>.convertBinToLatLng(map: GoogleMap, context: Context) {
    var latLng: LatLng
    val listLats: MutableList<LatLng> = mutableListOf()
    forEach { bin ->
        latLng = LatLng(bin.latitude, bin.longitude)
        listLats.add(latLng)
    }

    addMarkers(listLats, map, context)
}

fun addMarkers(locations: List<LatLng>, map: GoogleMap, context: Context) {
    locations.forEach {
        map.addMarker(
            MarkerOptions().position(it).icon(
                bitmapDescriptorFromVector(
                    context,
                    R.drawable.vector_bin
                )
            )
        )
    }
}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
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
package org.triniti.greensmart.utilities

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
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
import net.glxn.qrgen.android.QRCode
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Bin
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.data.db.entities.Product
import org.triniti.greensmart.ui.home.bins.OnLatLangListener
import java.util.*
import kotlin.math.ln

private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"

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

fun Activity.getLocation(onLatLangListener: OnLatLangListener) {
    val mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(this)

    mFusedLocationClient.lastLocation
        .addOnSuccessListener {
            it?.let {
                onLatLangListener.onSuccess(LatLng(it.latitude, it.longitude))
            }
        }
        .addOnFailureListener { e ->
            e.printStackTrace()
            onLatLangListener.onFailure(e.message)
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

fun convertBinToLatLng(list: List<Bin>, map: GoogleMap, context: Context) {
    var latLng: LatLng
    val listLats: MutableList<LatLng> = mutableListOf()
    list.forEach { bin ->
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

fun RecyclerView.runLayoutAnimation() {
    val controller =
        AnimationUtils.loadLayoutAnimation(context, R.anim.linear_animation)

    layoutAnimation = controller
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}

fun randomString(): String {
    val randSize = 12
    val random = Random()
    val sb = StringBuilder(randSize)
    for (i in 0 until randSize)
        sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
    return sb.toString()
}

fun generateQrCode(productCode: String, imageView: ImageView) {
    val myBitmap = QRCode.from(productCode).bitmap()
    imageView.setImageBitmap(myBitmap)
}

fun Product.toCart(userId: Int, points: Int, code: String, count: Int): Cart {
    return Cart(
        shopId = this.shopId,
        userId = userId,
        name = this.name,
        price = this.price.times(count),
        image = this.image,
        points = points,
        count = count,
        code = code,
        description = this.description
    )
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(factory: ViewModelProvider.NewInstanceFactory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}

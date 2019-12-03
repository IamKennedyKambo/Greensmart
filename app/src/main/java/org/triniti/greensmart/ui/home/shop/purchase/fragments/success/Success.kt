package org.triniti.greensmart.ui.home.shop.purchase.fragments.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_d_locate.*
import kotlinx.android.synthetic.main.layout_f_success.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.utilities.DataViewModel
import org.triniti.greensmart.utilities.addMenu
import org.triniti.greensmart.utilities.generateQrCode


class Success : Fragment(), KodeinAware, OnMapReadyCallback {

    override val kodein by kodein()
    private val dataViewModel: DataViewModel by instance()
    override fun onMapReady(p0: GoogleMap) {
        val milele = LatLng(-1.358040, 36.656764)
        val naivas = LatLng(-1.361926, 36.655183)

        val latLng = LatLng(-1.340529, 36.649208)
        val latLng1 = LatLng(-1.375498, 36.664274)

        val builder = LatLngBounds.Builder()

        //add them to builder
        builder.include(latLng)
        builder.include(latLng1)

        val bounds = builder.build()

        //get width and height to current display screen
//        val width = resources.displayMetrics.widthPixels
//        val height = resources.displayMetrics.heightPixels

        // 20% padding
//        val padding = (width * 0.20).toInt()

        //set latlong bounds
        p0.setLatLngBoundsForCameraTarget(bounds)

        //move camera to fill the bound to screen

        p0.addMarker(MarkerOptions().position(milele).title("Milele mall"))
        p0.addMarker(MarkerOptions().position(naivas).title("Naivas supermarket"))
        p0.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10))
    }

    private val sheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_HIDDEN -> {
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    tvTitle.visibility = View.GONE
                    setUpMap()
                }
                BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                }
                BottomSheetBehavior.STATE_DRAGGING -> {
                    tvTitle.visibility = View.VISIBLE
                }
                BottomSheetBehavior.STATE_SETTLING -> {
                }
            }
        }
    }
    private lateinit var sheetBehavior: BottomSheetBehavior<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.layout_f_success,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        dataViewModel.product.observe(viewLifecycleOwner, Observer { product ->
            tbSuccess.title = product.name
            Glide.with(context!!)
                .load(product.image)
                .into(ivSuccess)
            generateQrCode(product.code, ivCode)
        })

        dataViewModel.cart.observe(viewLifecycleOwner, Observer { cart ->
            tbSuccess.title = cart.name
            Glide.with(context!!)
                .load(cart.image)
                .into(ivSuccess)
            tvCount.text = cart.count.toString()
            generateQrCode(cart.code, ivCode)
        })

        addMenu(tbSuccess)

    }

    private fun setUpMap(): SupportMapFragment {
        val fm = childFragmentManager
        val supportMapFragment = SupportMapFragment.newInstance()
        fm.beginTransaction().replace(R.id.flMap, supportMapFragment, "MAP").commit()
        supportMapFragment.getMapAsync(this)

        return supportMapFragment
    }

    private fun removeMap() {
        childFragmentManager.beginTransaction().remove(setUpMap()).commit()
    }

    private fun initViews() {
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.addBottomSheetCallback(sheetCallback)

        tbSuccess.setupWithNavController(findNavController())
        tvTitle.setOnClickListener {
            sheetBehavior.state =
                BottomSheetBehavior.STATE_EXPANDED
        }

        ibClose.setOnClickListener {
            removeMap()
            tvTitle.visibility = View.VISIBLE
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}
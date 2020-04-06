package org.triniti.greensmart.ui.home.shop.purchase.fragments.success

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_d_locate.*
import kotlinx.android.synthetic.main.layout_f_success.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.ui.home.shop.mall.MallViewModel
import org.triniti.greensmart.ui.home.shop.mall.MallViewModelFactory
import org.triniti.greensmart.utilities.*
import java.io.IOException


class Success : Fragment(), KodeinAware, OnMapReadyCallback {

    override val kodein by kodein()
    private val dataViewModel: DataViewModel by instance()
    private lateinit var shopViewModel: MallViewModel
    private val mallFactory: MallViewModelFactory by instance()
    private val shops: MutableList<LatLng> = mutableListOf()
    private lateinit var map: GoogleMap

    override fun onMapReady(p0: GoogleMap) {
        setZoom(p0)
        addShops(p0)
        map = p0
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shopViewModel = activity?.getViewModel(mallFactory)!!
    }

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

        bindUI()

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

    private fun setZoom(map: GoogleMap){
        try {
            val address = Geocoder(context).getFromLocationName("Kenya", 1)
            if (address == null) {
                return
            } else {
                val loc = address[0]
                val pos =  LatLng(loc.latitude, loc.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 6f))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun bindUI() {
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
    }

    private fun addShops(map: GoogleMap) {
        Coroutines.main {
            shopViewModel.shops.await().observe(viewLifecycleOwner, Observer {
                it.forEach { shop ->
                    shops.add(LatLng(shop.latitude, shop.longitude))
                }


                addMarkers(shops, map, context!!, R.drawable.vector_bin)
            })
        }
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
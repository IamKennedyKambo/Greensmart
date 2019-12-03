package org.triniti.greensmart.ui.home.shop.purchase.fragments.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import kotlinx.android.synthetic.main.layout_d_confirmed.view.*
import kotlinx.android.synthetic.main.layout_f_points.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Product
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.preferences.PreferenceProvider
import org.triniti.greensmart.databinding.LayoutFPointsBinding
import org.triniti.greensmart.ui.auth.AuthViewModel
import org.triniti.greensmart.ui.auth.AuthViewModelFactory
import org.triniti.greensmart.ui.home.about.AboutViewModel
import org.triniti.greensmart.ui.home.about.AboutViewModelFactory
import org.triniti.greensmart.ui.home.cart.CartViewModel
import org.triniti.greensmart.ui.home.cart.CartViewModelFactory
import org.triniti.greensmart.utilities.DataViewModel
import org.triniti.greensmart.utilities.getViewModel
import org.triniti.greensmart.utilities.randomString
import org.triniti.greensmart.utilities.toCart


class Points : Fragment(), KodeinAware, SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        tvCount.text = p1.toString()
        count = p1
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override val kodein by kodein()
    private val dataViewModel: DataViewModel by instance()
    private var product: Product? = null
    private val prefs: PreferenceProvider by instance()
    private lateinit var binding: LayoutFPointsBinding
    private val cartFactory: CartViewModelFactory by instance()
    private val aboutFactory: AboutViewModelFactory by instance()
    private val userFactory: AuthViewModelFactory by instance()
    private lateinit var cartViewModel: CartViewModel
    private lateinit var aboutViewModel: AboutViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var user: User
    private var count: Int? = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_f_points,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModels()
        setListeners()

        dataViewModel.product.observe(viewLifecycleOwner, Observer {
            product = it
            binding.product = it
        })

        aboutViewModel.user.observe(viewLifecycleOwner, Observer {
            user = it
        })
    }

    private fun showConfirm(pts: Int) {
        MaterialDialog(requireContext()).show {
            customView(R.layout.layout_d_confirmed)
            val view = getCustomView()
            val confirm = view.butConfirm
            val cancel = view.butCancel
            val heading = view.tvWarning
            val content = view.tvMessage

            content.text = "Proceed with purchasing $count * ${product?.name}."
            heading.text = "This will deduct $pts points from your usable points."

            confirm.setOnClickListener {
                cartViewModel.insertEntry(
                    product!!.toCart(
                        prefs.getUserId()!!,
                        pts,
                        randomString(),
                        count!!
                    )
                )

                val newUser = user.copy(
                    usable_points = user.usable_points?.minus(pts)
                )

                aboutViewModel.updateUser(newUser)
                dataViewModel.setSuccess(true)
                dataViewModel.setProduct(product!!)

                dismiss()
            }

            cancel.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun showError() {
        MaterialDialog(requireContext()).show {
            customView(R.layout.layout_d_rejected)
            val view = getCustomView()
            val cancel = view.butCancel

            cancel.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun canPurchase(userPoints: Int, billed: Int): Boolean {
        return userPoints > billed
    }

    private fun initViewModels() {
        cartViewModel = activity?.getViewModel(cartFactory)!!
        aboutViewModel = activity?.getViewModel(aboutFactory)!!
        authViewModel = activity?.getViewModel(userFactory)!!
    }

    private fun setListeners() {
        sbPoints.setOnSeekBarChangeListener(this)

        butPay.setOnClickListener {
            val billed = count!!.times(product?.points!!)

            if (canPurchase(user.usable_points!!, billed))
                showConfirm(billed)
            else
                showError()
        }
    }
}
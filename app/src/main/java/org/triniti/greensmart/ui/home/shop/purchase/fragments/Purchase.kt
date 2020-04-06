package org.triniti.greensmart.ui.home.shop.purchase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_f_purchase.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.ui.home.cart.CartViewModel
import org.triniti.greensmart.ui.home.cart.CartViewModelFactory
import org.triniti.greensmart.ui.home.shop.purchase.FragmentAdapter
import org.triniti.greensmart.ui.home.shop.purchase.fragments.payment.Points
import org.triniti.greensmart.utilities.*

class Purchase : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val args: PurchaseArgs by navArgs()
    private val dataViewModel: DataViewModel by instance()
    private lateinit var cartViewModel: CartViewModel
    private val cartFactory: CartViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        cartViewModel = activity?.getViewModel(cartFactory)!!
        return inflater.inflate(R.layout.layout_f_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbPurchase.setupWithNavController(findNavController())
        tbPurchase.title = args.product.name
        addMenu(tbPurchase)

        Glide.with(context!!)
            .load(args.product.image)
            .into(ivProduct)

        tvDescription.text = args.product.description
        dataViewModel.setProduct(args.product)

        dataViewModel.success.observe(viewLifecycleOwner, Observer {
            if (it) {
                Snackbar.make(view, "Successful purchase", Snackbar.LENGTH_SHORT).show()
                dataViewModel.setSuccess(false)
            }
        })

        setUpViewPager(vpPurchase)

    }

    private fun setUpViewPager(viewPager: ViewPager) {
        val fragmentAdapter =
            FragmentAdapter(childFragmentManager)
        fragmentAdapter.addFragment(Points())

        viewPager.adapter = fragmentAdapter
    }
}

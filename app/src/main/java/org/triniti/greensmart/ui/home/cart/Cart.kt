package org.triniti.greensmart.ui.home.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_cart.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.data.preferences.PreferenceProvider
import org.triniti.greensmart.ui.home.about.AboutViewModel
import org.triniti.greensmart.ui.home.about.AboutViewModelFactory
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.DataViewModel
import org.triniti.greensmart.utilities.getViewModel
import org.triniti.greensmart.utilities.runLayoutAnimation

class Cart : Fragment(), KodeinAware {

    override val kodein by kodein()
    private lateinit var cartViewModel: CartViewModel
    private lateinit var aboutViewModel: AboutViewModel
    private val dataViewModel: DataViewModel by instance()
    private val cartFactory: CartViewModelFactory by instance()
    private val aboutFactory: AboutViewModelFactory by instance()
    private val prefs: PreferenceProvider by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_f_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbCart.setupWithNavController(findNavController())
        cartViewModel = activity?.getViewModel(cartFactory)!!
        aboutViewModel = activity?.getViewModel(aboutFactory)!!

        bindUI()
    }

    private fun bindUI() {
        cartViewModel.id = prefs.getUserId()

        Coroutines.main {
            cartViewModel.cart.await().observe(viewLifecycleOwner, Observer {
                Log.i("Size", it.size.toString())
                setUpRecyclerView(it.toCartItems())
            })
        }
    }

    private fun setUpRecyclerView(products: List<CartItems>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(products)
        }

        val decor = DividerItemDecoration(context, RecyclerView.VERTICAL)

        rvCart.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(decor)
            runLayoutAnimation()
        }
    }

    private fun List<Cart>.toCartItems(): List<CartItems> {
        return map {
            CartItems(it, findNavController(), dataViewModel, cartViewModel)
        }
    }
}
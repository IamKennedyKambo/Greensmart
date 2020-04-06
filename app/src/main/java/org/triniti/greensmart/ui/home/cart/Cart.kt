package org.triniti.greensmart.ui.home.cart

import android.os.Bundle
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
import com.bumptech.glide.Glide
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_cart.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.ui.home.about.AboutViewModel
import org.triniti.greensmart.ui.home.about.AboutViewModelFactory
import org.triniti.greensmart.utilities.*

class Cart : Fragment(), KodeinAware, CartItems.OnItemDeletedListener {

    override val kodein by kodein()
    private val dataViewModel: DataViewModel by instance()
    private lateinit var cartViewModel: CartViewModel
    private val cartFactory: CartViewModelFactory by instance()
    private lateinit var aboutViewModel: AboutViewModel
    private val aboutFactory: AboutViewModelFactory by instance()
    private var oldList: MutableList<CartItems> = mutableListOf()
    private lateinit var user: User

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

        ctlCart.setOnDragListener(null)

        Glide.with(this)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ3_D3TsonbyTCGRJMfbdyv5ujQZtSkZeywybe4HaSvCqDVoa6c")
            .centerCrop()
            .into(ivBanner)

        bindUI()

        aboutViewModel.user.observe(viewLifecycleOwner, Observer {
            user = it
        })
    }

    private fun bindUI() {
        Coroutines.main {
            cartViewModel.cart.await().observe(viewLifecycleOwner, Observer {
                oldList = it.toCartItems().toMutableList()
                setUpRecyclerView(it.toCartItems())
            })
        }
    }

    private fun setUpRecyclerView(products: List<CartItems>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            clear()
            addAll(products)
        }

        val decor = DividerItemDecoration(context, RecyclerView.VERTICAL)

        if (products.isEmpty()) {
            rvCart.visibility = View.GONE
            lavCart.visibility = View.VISIBLE
        }

        rvCart.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(decor)
            runLayoutAnimation()
        }
    }

    private fun List<Cart>.toCartItems(): List<CartItems> {
        return map {
            CartItems(
                it,
                this@Cart
            )
        }
    }

    override fun onItemDeleted(cart: Cart) {
        view?.showSnackBar("${cart.count} x ${cart.name} was deleted.")

        val oldUser = aboutViewModel.user.value
        val usable = oldUser?.usable_points?.plus(cart.points)
        val usedPoints = oldUser?.used_points?.minus(cart.points)
        val level = setLevel(oldUser?.used_points?.minus(cart.points)!!)

        val newUser = oldUser.copy(
            usable_points = usable,
            used_points = usedPoints,
            level = level
        )

        cartViewModel.deleteEntry(
            cart.id!!
        )

        aboutViewModel.updateUser(newUser)
    }

    override fun onItemSelected(cart: Cart) {
        dataViewModel.setCart(cart)
        findNavController().navigate(R.id.destination_success)
    }
}
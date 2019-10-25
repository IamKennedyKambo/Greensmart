package org.triniti.greensmart.ui.home.shop.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_cart.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.pojos.Product

class Cart : Fragment() {
    private val products: MutableList<CartItems> = mutableListOf()

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

        val decor = DividerItemDecoration(context, RecyclerView.VERTICAL)

        products.clear()
        getProductList()

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(products)
        }
        rvCart.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(decor)
        }
    }

    fun getProductList(): MutableList<CartItems>{
        products.add(
            CartItems(
                Product(
                    1,
                    2,
                    "1 Kg Cabbage",
                    "20",
                    15,
                    "https://www.tingstad.com/fixed/images/Main/1516212547/CG0921302.png"
                ), findNavController()
            )
        )
        products.add(
            CartItems(
                Product(
                    1,
                    2,
                    "1 Kg Onions",
                    "500",
                    270,
                    "https://www.kroger.com/product/images/large/front/0000000004093"
                ), findNavController()
            )
        )
        products.add(
            CartItems(
                Product(
                    1,
                    2,
                    "1 Kg Tomato",
                    "90",
                    55,
                    "https://www.levarht.com/wp-content/uploads/2018/05/55-tomato.png"
                ), findNavController()
            )
        )
        products.add(
            CartItems(
                Product(
                    1,
                    2,
                    "500g Avocado",
                    "30",
                    20,
                    "https://organicfoodsandcafe.com/wp-content/uploads/2015/09/4521-pg.png"
                ), findNavController()
            )
        )
        products.add(
            CartItems(
                Product(
                    1,
                    2,
                    "500g Avocado",
                    "30",
                    20,
                    "https://organicfoodsandcafe.com/wp-content/uploads/2015/09/4521-pg.png"
                ), findNavController()
            )
        )
        products.add(
            CartItems(
                Product(
                    1,
                    2,
                    "500g Avocado",
                    "30",
                    20,
                    "https://organicfoodsandcafe.com/wp-content/uploads/2015/09/4521-pg.png"
                ), findNavController()
            )
        )
        return products
    }
}
package org.triniti.greensmart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_single.*
import org.triniti.greensmart.data.pojos.Product
import org.triniti.greensmart.ui.shop.single.SingleArgs
import org.triniti.greensmart.ui.shop.single.SingleItems
import org.triniti.greensmart.utilities.addMenu

class Kevian : Fragment() {
    private val products: MutableList<SingleItems> = mutableListOf()
    private val args: SingleArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_f_single, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbSingle.setupWithNavController(findNavController())
        addMenu(tbSingle)
        tbSingle.title = args.shopName
        products.clear()
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Afia",
                    "20",
                    15,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTS6tunI0UQZRRTSwto-5LjY1R9KlG84uECewFKUHTJ9N956vZm"
                ), findNavController()
            )
        )
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Afia mango",
                    "500",
                    270,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQO9y43tuODI9c4wOw9RUYdUll7QhajadWcQi5VPKI7jbvou8Bz"
                ), findNavController()
            )
        )
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Pick and peel",
                    "90",
                    55,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRNrCsSb-uXkoVrd5paCE5hCalbIuCMPd7YlhcgF4hkpRlxy7P9"
                ), findNavController()
            )
        )
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Pick and peel orange",
                    "30",
                    20,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSMamzQdaeLqMm_Ppuz1bTPEb9tpnSIAhu-tPrMLFav7QnwANpg"
                ), findNavController()
            )
        )

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(products)
        }

        val decor = DividerItemDecoration(context, RecyclerView.VERTICAL)

        rvSingle.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(decor)
        }
    }
}
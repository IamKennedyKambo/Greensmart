package org.triniti.greensmart

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class brookside : Fragment() {
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
                    "Brook Vanilla",
                    "20",
                    15,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS5sLiT9MeVgSamg31LHGvRtSSa_1oU6fmBXpZuw6oUsxaE-TrH"
                ), findNavController()
            )
        )
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Brook yorghurt",
                    "500",
                    270,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRw2yTOZVIl2NQ_3TUXcE2zwWUry-kdF74zCPCs_mjBv__EmKnI"
                ), findNavController()
            )
        )
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Brookside strawberry",
                    "90",
                    55,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTmyDKaEfvYcAbZ1hyUOTWXPEdw2jniItJbFbVm_gQ1MBxoyXa3"
                ), findNavController()
            )
        )
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Delamere yoghurt",
                    "30",
                    20,
                    "https://www.masoko.com/media/catalog/product/cache/image/700x700/e9c3970ab036de70892d86c6d221abfe/d/e/delamere-pear-caramel_1.png"
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
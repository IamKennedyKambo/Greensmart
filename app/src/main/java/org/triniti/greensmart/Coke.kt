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

class Coke : Fragment() {
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
                    "Coke zero 1l",
                    "20",
                    15,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR1zC_uMAkFAdXqBumvkflP-8tuUTeCUncSS2qq41lZrpdOA5bx"
                ), findNavController()
            )
        )
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Coca cola",
                    "500",
                    270,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTC5p_7NfGDc1_utFdM-rALS5ruZR0mUJMRr4j3IZa8kbvsT-_R"
                ), findNavController()
            )
        )
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Fanta",
                    "90",
                    55,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTIeqz1sB3-2jcBijiaO9tMWudT3ds5LyYcjVTSuTeYIWvI0z29"
                ), findNavController()
            )
        )
        products.add(
            SingleItems(
                Product(
                    1,
                    2,
                    "Sprite",
                    "30",
                    20,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTOLlh0qfrrFjCqMxl0yI44C9dLxA1RoQX7qXlgxH-C-l_Y1vPR"
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
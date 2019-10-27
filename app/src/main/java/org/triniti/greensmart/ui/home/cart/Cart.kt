package org.triniti.greensmart.ui.home.cart

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

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(products)
        }
        rvCart.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(decor)
        }
    }
}
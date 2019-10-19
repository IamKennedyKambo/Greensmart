package org.triniti.greensmart.ui.shop.single

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
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_single.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.pojos.Product
import org.triniti.greensmart.utilities.addMenu
import org.triniti.greensmart.ui.shop.single.SingleArgs


class Single : Fragment() {
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

        args.shop.it.forEach {
            products.add(SingleItems(it, findNavController()))
        }

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(products)
        }

        val decor = DividerItemDecoration(context, VERTICAL)

        rvSingle.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(decor)
        }
    }
}
package org.triniti.greensmart.ui.home.shop.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_single.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Items
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.addMenu
import org.triniti.greensmart.utilities.showSnackBar
import org.triniti.greensmart.utilities.showToast


class Products : Fragment(), KodeinAware {

    override val kodein by kodein()
    private lateinit var productsViewModel: ProductsViewModel
    private val factory: ProductsViewModelFactory by instance()
    private val args by navArgs<ProductsArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productsViewModel = ViewModelProviders.of(this, factory).get(ProductsViewModel::class.java)
        return inflater.inflate(R.layout.layout_f_single, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbSingle.setupWithNavController(findNavController())
        addMenu(tbSingle)

        tbSingle.title = args.shopName

        productsViewModel.id = args.shopId

        Coroutines.main {
            productsViewModel.items.await().observe(this, Observer {
                setUpRecyclerView(it.toSingleItems())
            })
        }
    }

    private fun setUpRecyclerView(products: List<ProductsItems>) {
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

    private fun List<Items>.toSingleItems(): List<ProductsItems> {
        return map {
            ProductsItems(it, findNavController())
        }
    }
}
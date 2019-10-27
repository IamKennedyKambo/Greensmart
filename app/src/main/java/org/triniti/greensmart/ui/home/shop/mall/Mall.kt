package org.triniti.greensmart.ui.home.shop.mall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_mall.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Shop
import org.triniti.greensmart.ui.home.shop.single.ProductsViewModel
import org.triniti.greensmart.ui.home.shop.single.ProductsViewModelFactory
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.addMenu
import org.triniti.greensmart.utilities.calculateNoOfColumns
import org.triniti.greensmart.utilities.showToast

class Mall : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var mallViewModel: MallViewModel
    private val mallFactory: MallViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_f_mall, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbMall.setupWithNavController(findNavController())
        addMenu(tbMall)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mallViewModel = ViewModelProviders.of(this, mallFactory).get(MallViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = Coroutines.main {
        mallViewModel.shops.await().observe(this, Observer {
            setUpRecyclerView(it.toMallItems())
        })
    }

    private fun setUpRecyclerView(shops: List<MallItems>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(shops)
        }

        rvMall.apply {
            layoutManager = GridLayoutManager(context, calculateNoOfColumns(context, 180f))
            setHasFixedSize(true)
            adapter = groupAdapter
        }
    }

    private fun List<Shop>.toMallItems(): List<MallItems> {
        return this.map {
            MallItems(it, findNavController())
        }
    }
}
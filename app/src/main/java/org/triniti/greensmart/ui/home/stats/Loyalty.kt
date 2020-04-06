package org.triniti.greensmart.ui.home.stats

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_loyalty.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.News
import org.triniti.greensmart.ui.home.about.AboutViewModel
import org.triniti.greensmart.ui.home.about.AboutViewModelFactory
import org.triniti.greensmart.ui.home.cart.CartViewModel
import org.triniti.greensmart.ui.home.cart.CartViewModelFactory
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.getViewModel


class Loyalty : Fragment(), KodeinAware {

    override val kodein by kodein()
    private lateinit var aboutViewModel: AboutViewModel
    private val aboutFactory: AboutViewModelFactory by instance()
    private lateinit var newsViewModel: NewsViewModel
    private val newsFactory: NewsViewModelFactory by instance()
    private var usedPoints: Int = 0
    private var unusedPoints: Int = 0
    private val snapper: LinearSnapHelper by lazy {
        LinearSnapHelper()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutViewModel = activity?.getViewModel(aboutFactory)!!
        newsViewModel = activity?.getViewModel(newsFactory)!!

        return inflater.inflate(
            R.layout.layout_f_loyalty,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivMarket.setOnClickListener {
            findNavController().navigate(R.id.destination_mall)
        }

        unusedPoints = 0
        usedPoints = 0

        bindUI()
    }

    private fun AppCompatTextView.setCountAnimation(upTo: Int) {
        val valueAnimator = ValueAnimator.ofInt(0, upTo)
        valueAnimator.duration = 750

        valueAnimator.addUpdateListener { animator -> text = animator.animatedValue.toString() }
        valueAnimator.start()
    }

    private fun bindUI() {
        aboutViewModel.user.observe(viewLifecycleOwner, Observer {
            unusedPoints = it.usable_points!!
            usedPoints = it.used_points!!
            val level = it.level

            tvVal1.setCountAnimation(usedPoints)
            tvVal2.setCountAnimation(unusedPoints)
            tvVal3.setCountAnimation(it.level!!)

            if (level!! > 1){
                lavLoyalty.setAnimation(R.raw.celly)
            }
        })

        Coroutines.main {
            newsViewModel.news.await().observe(viewLifecycleOwner, Observer {
                setUpRecyclerView(it.toNewsItems())
            })
        }
    }

    private fun setUpRecyclerView(list: List<NewsItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(list)
        }

        val manager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, true)
        manager.apply {
            stackFromEnd = true
        }

        rvNews.apply {
            layoutManager = manager
            hasFixedSize()
            adapter = groupAdapter
        }

        snapper.attachToRecyclerView(rvNews)
    }
}

private fun List<News>.toNewsItems(): List<NewsItem> {
    return map {
        NewsItem(it)
    }
}

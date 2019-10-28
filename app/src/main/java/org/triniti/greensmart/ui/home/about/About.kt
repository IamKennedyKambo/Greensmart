package org.triniti.greensmart.ui.home.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_about.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.pojos.Setting
import org.triniti.greensmart.databinding.LayoutFAboutBinding

class About : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var aboutViewModel: AboutViewModel
    private val factory: AboutViewModelFactory by instance()

    private val settings: MutableList<AboutItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: LayoutFAboutBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_f_about, container, false)
        aboutViewModel = ViewModelProviders.of(this, factory).get(AboutViewModel::class.java)
        aboutViewModel.context = context
        binding.viewModel = aboutViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        settings.add(
            AboutItem(
                Setting(
                    "Like us",
                    context!!.getDrawable(R.drawable.vector_facebook)!!
                )
            )
        )

        settings.add(
            AboutItem(
                Setting(
                    "Follow us",
                    context!!.getDrawable(R.drawable.vector_twitter)!!
                )
            )
        )
        settings.add(
            AboutItem(
                Setting(
                    "View us",
                    context!!.getDrawable(R.drawable.vector_open)!!
                )
            )
        )
        settings.add(
            AboutItem(
                Setting(
                    "Sponsors",
                    context!!.getDrawable(R.drawable.vector_sponsors)!!
                )
            )
        )
        settings.add(
            AboutItem(
                Setting(
                    "About us",
                    context!!.getDrawable(R.drawable.vector_about)!!
                )
            )
        )

        settings.add(
            AboutItem(
                Setting(
                    "Join collectors",
                    context!!.getDrawable(R.drawable.vector_signup)!!
                )
            )
        )

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(
                settings
            )
        }

        rvSetting.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = groupAdapter
        }
    }
}
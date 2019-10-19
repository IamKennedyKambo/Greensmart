package org.triniti.greensmart.ui.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_about.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.pojos.Setting
import org.triniti.greensmart.ui.main.settings.AboutItem

class About : Fragment() {
    private val settings: MutableList<AboutItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_f_about, container, false)
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
                    context!!.getDrawable(R.drawable.vector_smiley)!!
                )
            )
        )
        settings.add(
            AboutItem(
                Setting(
                    "About us",
                    context!!.getDrawable(R.drawable.vector_web)!!
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
            adapter = groupAdapter
        }
    }
}
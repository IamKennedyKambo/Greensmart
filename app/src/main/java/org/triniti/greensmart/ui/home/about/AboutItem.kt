package org.triniti.greensmart.ui.home.about

import android.content.Intent
import android.net.Uri
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_settings.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.pojos.Setting

class AboutItem(private val setting: Setting) : Item<ViewHolder>() {
    override fun getLayout() = R.layout.layout_c_settings

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val view = viewHolder.itemView
        view.ivSetting.setImageDrawable(setting.image)
        view.tvSetting.text = setting.name

        view.llSettings.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            when (position) {
                0 -> {
                    intent.data = Uri.parse("https://www.google.com")
                }
                1 -> {
                    intent.data = Uri.parse("https://www.google.com")
                }
                2 -> {
                    intent.data = Uri.parse("https://www.google.com")
                }
                3 -> {
                    intent.data = Uri.parse("https://www.google.com")
                }
                4 -> {
                    intent.data = Uri.parse("https://www.greensmart.co.ke")
                }
            }
            view.context.startActivity(Intent.createChooser(intent, "Choose app"))
        }
    }
}
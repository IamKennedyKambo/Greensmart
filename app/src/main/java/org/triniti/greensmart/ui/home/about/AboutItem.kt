package org.triniti.greensmart.ui.home.about

import android.content.Intent
import android.net.Uri
import android.view.View
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_settings.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Setting

class AboutItem(private val setting: Setting, private val logOutRequest: OnLogOutRequest) :
    Item<ViewHolder>() {

    override fun getLayout() = R.layout.layout_c_settings

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val view = viewHolder.itemView
        view.ivSetting.setImageDrawable(setting.image)
        view.tvSetting.text = setting.name

        view.llSettings.setOnClickListener {
            when (position) {
                0 -> {
                    view.createIntent("https://www.greensmart.co.ke/#extras/sponsors")
                }
                1 -> {
                    view.createIntent("https://www.greensmart.co.ke/#extras/sponsors")
                }
                2 -> {
                    view.createIntent("https://www.greensmart.co.ke/#extras/sponsors")
                }
                3 -> {
                    view.createIntent("https://www.greensmart.co.ke")
                }
                4 -> {
                    logOutRequest.requestLogout(true)
                }
            }
        }
    }

    private fun View.createIntent(url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        context.startActivity(Intent.createChooser(intent, "Choose app"))
    }

    interface OnLogOutRequest {
        fun requestLogout(request: Boolean)
    }
}
package org.triniti.greensmart.ui.home.stats

import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_news.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.News

class NewsItem(private val news: News) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.layout_c_news

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val view = viewHolder.itemView
        val context = view.context

        Glide.with(context).load(news.image).centerCrop().into(view.ivBack)

        view.tvTitle.text = news.title
        view.tvMessage.text = news.content
    }
}
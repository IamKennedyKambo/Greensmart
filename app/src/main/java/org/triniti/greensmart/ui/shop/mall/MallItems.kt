package org.triniti.greensmart.ui.shop.mall

import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_malls.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.pojos.Shop

class MallItems(val shop: Shop, private val navController: NavController) : Item<ViewHolder>() {
    override fun getLayout() = R.layout.layout_c_malls

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.itemView.context)
            .load(shop.sl)
            .into(viewHolder.itemView.ivShop)

        viewHolder.itemView.tvShop.text = shop.sn

        viewHolder.itemView.setOnClickListener {
            val action = MallDirections.actionDestinationMallToDestinationSingle(shop.si, shop.sn, shop)
            navController.navigate(action)
        }
    }
}
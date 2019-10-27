package org.triniti.greensmart.ui.home.shop.mall

import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_malls.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Shop
import org.triniti.greensmart.ui.home.shop.single.ProductsViewModel
import org.triniti.greensmart.utilities.showToast

class MallItems(
    val shop: Shop,
    private val navController: NavController
) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.layout_c_malls

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.itemView.context)
            .load(shop.logo)
            .into(viewHolder.itemView.ivShop)

        viewHolder.itemView.tvShop.text = shop.name

        viewHolder.itemView.clParent.setOnClickListener {
            val action =
                MallDirections.actionDestinationMallToDestinationSingle(shop.name, shop.id.toInt())
            navController.navigate(action)
        }
    }
}
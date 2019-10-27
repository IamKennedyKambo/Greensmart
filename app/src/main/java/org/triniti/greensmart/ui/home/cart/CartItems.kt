package org.triniti.greensmart.ui.home.cart

import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_cart.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Items

class CartItems(val items: Items, private val navController: NavController) : Item<ViewHolder>(){
    override fun getLayout() = R.layout.layout_c_cart

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val view = viewHolder.itemView
        Glide.with(view.context)
            .load(items.image)
            .into(view.ivProduct)

        view.tvName.text = items.name
        view.tvPrice.text = items.points.toString()

        view.butClaim.setOnClickListener {

        }
    }
}
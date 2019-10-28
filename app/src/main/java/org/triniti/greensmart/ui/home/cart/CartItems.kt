package org.triniti.greensmart.ui.home.cart

import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_cart.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Product

class CartItems(val product: Product, private val navController: NavController) : Item<ViewHolder>(){
    override fun getLayout() = R.layout.layout_c_cart

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val view = viewHolder.itemView
        Glide.with(view.context)
            .load(product.image)
            .into(view.ivProduct)

        view.tvName.text = product.name
        view.tvPrice.text = product.points.toString()

        view.butClaim.setOnClickListener {

        }
    }
}
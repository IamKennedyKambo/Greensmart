package org.triniti.greensmart.ui.shop.single

import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_single.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.pojos.Product

class SingleItems(private val product: Product, private val navController: NavController) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.layout_c_single

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val view = viewHolder.itemView
        view.tvName.text = product.name
        view.tvPrice.text = product.price + " /= or " + product.points.toString() + "pts"
        Glide.with(view.context)
            .load(product.imageUrl)
            .into(view.ivProduct)

        view.clProduct.setOnClickListener {
            val action = SingleDirections.actionDestinationSingleToDestinationPurchase(product)
            navController.navigate(action)
        }
    }
}
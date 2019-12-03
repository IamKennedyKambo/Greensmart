package org.triniti.greensmart.ui.home.cart

import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_cart.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.utilities.DataViewModel

class CartItems(
    private val cart: Cart,
    private val navController: NavController,
    private val viewModel: DataViewModel,
    private val cartViewModel: CartViewModel
) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.layout_c_cart

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val view = viewHolder.itemView
        Glide.with(view.context)
            .load(cart.image)
            .into(view.ivProduct)

        view.tvName.text = cart.name
        view.tvPrice.text = cart.count.toString()

        view.butClaim.setOnClickListener {
            viewModel.setCart(cart)
            navController.navigate(R.id.destination_success)
        }
    }
}
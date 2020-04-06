package org.triniti.greensmart.ui.home.cart

import android.content.Context
import android.view.View
import androidx.navigation.NavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_c_cart.view.*
import kotlinx.android.synthetic.main.layout_d_confirmed.view.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.ui.home.about.AboutViewModel
import org.triniti.greensmart.utilities.DataViewModel
import org.triniti.greensmart.utilities.setLevel
import org.triniti.greensmart.utilities.showToast

class CartItems(
    private val cart: Cart,
    private val onItemDeletedListener: OnItemDeletedListener
) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.layout_c_cart

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val view = viewHolder.itemView
        Glide.with(view.context)
            .load(cart.image)
            .into(view.ivProduct)

        view.tvName.text = cart.name
        view.tvPrice.text = "x ${cart.count}"

        view.butClaim.setOnClickListener {
            onItemDeletedListener.onItemSelected(cart)
        }

        if (cart.redeemed == 1) {
            view.butClaim.isEnabled = false
            view.butDelete.visibility = View.GONE
        }

        view.butDelete.setOnClickListener {
            showConfirm(cart, view.context)
        }
    }

    private fun showConfirm(cart: Cart, context: Context) {
        MaterialDialog(context).show {
            customView(R.layout.layout_d_confirmed)
            val view = getCustomView()
            val confirm = view.butConfirm
            val cancel = view.butCancel
            val heading = view.tvWarning
            val content = view.tvMessage

            content.text = "Proceed with deleting ${cart.name}"
            heading.text = "This will add ${cart.points} points to your usable points."

            confirm.setOnClickListener {
                onItemDeletedListener.onItemDeleted(cart)
                dismiss()
            }

            cancel.setOnClickListener {
                dismiss()
            }
        }
    }

    interface OnItemDeletedListener {
        fun onItemDeleted(cart: Cart)
        fun onItemSelected(cart: Cart)
    }
}
package org.triniti.greensmart.ui.home.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_f_purchase.*
import org.triniti.greensmart.R
import org.triniti.greensmart.utilities.addMenu

class Purchase : Fragment() {
    private val args: PurchaseArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_f_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbPurchase.setupWithNavController(findNavController())
        tbPurchase.title = args.product.name
        addMenu(tbPurchase)

        Glide.with(context!!)
            .load(args.product.image)
            .into(ivProduct)

//        val actions = PurchaseDirections.actionDestinationPurchaseToDestinationSuccess(args.product)

        tvDescription.text = args.product.description

        butNext.setOnClickListener {
            vfPurchase.setInAnimation(context!!, R.anim.slide_in_right)
            vfPurchase.setOutAnimation(context!!, R.anim.slide_out_left)
            vfPurchase.showNext()
        }

        butPrev.setOnClickListener {
            vfPurchase.setInAnimation(context!!, android.R.anim.slide_in_left)
            vfPurchase.setOutAnimation(context!!, android.R.anim.slide_out_right)
            vfPurchase.showPrevious()
        }
    }
}

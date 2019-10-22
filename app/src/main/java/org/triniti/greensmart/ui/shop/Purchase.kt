package org.triniti.greensmart.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.RotateAnimation
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
        return inflater.inflate(org.triniti.greensmart.R.layout.layout_f_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbPurchase.setupWithNavController(findNavController())
        tbPurchase.title = args.product.name
        addMenu(tbPurchase)

        Glide.with(context!!)
            .load(args.product.imageUrl)
            .into(ivProduct)

        butNext.setOnClickListener {

        }

//        val ranim = loadAnimation(context, R.anim.rotate) as RotateAnimation
//        ranim.fillAfter = true //For the textview to remain at the same place after the rotation
//        tvTitle.animation = ranim

        val actions = PurchaseDirections.actionDestinationPurchaseToDestinationSuccess(args.product)

//        butPoints.setOnClickListener {
//            findNavController().navigate(actions)
//        }
//
//        butCash.setOnClickListener {
//            findNavController().navigate(actions)
//        }
    }
}
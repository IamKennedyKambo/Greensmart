package org.triniti.greensmart.ui.home.shop.purchase.fragments.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.layout_f_points.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Product
import org.triniti.greensmart.databinding.LayoutFCashBinding
import org.triniti.greensmart.utilities.DataViewModel

class Cash : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val dataViewModel: DataViewModel by instance()
    private var product: Product? = null
    private lateinit var binding: LayoutFCashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_f_cash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataViewModel.product.observe(viewLifecycleOwner, Observer {
            product = it
            binding.product = it
        })

        sbPoints.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvCount.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        butPay.setOnClickListener {
            dataViewModel.setProduct(product!!)
            findNavController().navigate(R.id.destination_success)
        }
    }
}
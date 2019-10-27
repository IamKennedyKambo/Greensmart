package org.triniti.greensmart.ui.home.bins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_d_complete.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.ui.home.about.AboutViewModel
import org.triniti.greensmart.ui.home.about.AboutViewModelFactory
import org.triniti.greensmart.utilities.showSnackBar

class Complete : BottomSheetDialogFragment(), KodeinAware, OnCompletionListener {

    override fun onCompletion(user: User) {
        dismiss()
    }

    override val kodein by kodein()

    private lateinit var aboutViewModel: AboutViewModel
    private val factory: AboutViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_d_complete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutViewModel = ViewModelProviders.of(this, factory).get(AboutViewModel::class.java)

        butDone.setOnClickListener {
            val cardId = etCard.text.toString()

            if (cardId.isEmpty() || cardId.isBlank()) {
                view.showSnackBar("Please enter the ID on your card")
            } else if (cardId.length != 6) {
                view.showSnackBar("Please fill in the correct ID")
            }

            aboutViewModel.user.observe(this, Observer {
                it.apply {
                    this.cardId = cardId
                    aboutViewModel.updateUser(this)
                }
            })
        }
    }
}
package org.triniti.greensmart.ui.home.complete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_d_complete.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.ui.auth.AuthViewModel
import org.triniti.greensmart.ui.auth.AuthViewModelFactory
import org.triniti.greensmart.ui.home.about.AboutViewModel
import org.triniti.greensmart.ui.home.about.AboutViewModelFactory
import org.triniti.greensmart.utilities.DataViewModel
import org.triniti.greensmart.utilities.getViewModel
import org.triniti.greensmart.utilities.showSnackBar
import org.triniti.greensmart.utilities.showToast

class Complete : BottomSheetDialogFragment(), KodeinAware,
    OnCompletionListener {

    override fun onCompletion(user: User) {
        authViewModel.saveUser(user)
        if (isAdded && isVisible) {
            dismiss()
        }
    }

    override fun onFailure(message: String) {
        view?.showSnackBar(message)
    }

    override val kodein by kodein()

    private lateinit var aboutViewModel: AboutViewModel
    private lateinit var authViewModel: AuthViewModel
    private val userFactory: AuthViewModelFactory by instance()
    private val aboutFactory: AboutViewModelFactory by instance()
    private val dataViewModel: DataViewModel by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_d_complete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = activity?.getViewModel(userFactory)!!
        aboutViewModel = activity?.getViewModel(aboutFactory)!!
        aboutViewModel.completionListener = this

        butDone.setOnClickListener {
            val cardId = etCard.text.toString()

            if (cardId.isEmpty() || cardId.isBlank()) {
                context?.showToast("Please enter the ID on your card")
            } else if (cardId.length != 6) {
                context?.showToast("Please fill in the correct ID")
            } else {
                authViewModel.getLoggedInUser().observe(viewLifecycleOwner, Observer {
                    val newUser = it.copy(cardId = cardId)
                    aboutViewModel.updateUser(newUser)
                    dataViewModel.updateComplete(yes = true)
                })
            }
        }
    }
}
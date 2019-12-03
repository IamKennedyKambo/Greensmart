package org.triniti.greensmart.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.layout_f_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.databinding.LayoutFLoginBinding
import org.triniti.greensmart.ui.MainActivity
import org.triniti.greensmart.ui.auth.AuthListener
import org.triniti.greensmart.ui.auth.AuthViewModel
import org.triniti.greensmart.ui.auth.AuthViewModelFactory
import org.triniti.greensmart.utilities.showSnackBar

class Signin : Fragment(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: LayoutFLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_f_login, container, false)

        val authViewModel = ViewModelProviders.of(this, factory)
            .get(AuthViewModel::class.java)

        authViewModel.authListener = this

        binding.viewModel = authViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivLogo.setImageResource(R.mipmap.ic_launcher)

        tvSignup.setOnClickListener {
            findNavController().navigate(R.id.destination_signup)
        }
    }

    override fun onStarted() {
        butLogin.visibility = View.INVISIBLE
        lavAuthenticate.visibility = View.VISIBLE
    }

    override fun onSuccess(user: User?) {
        butLogin.visibility = View.VISIBLE
        lavAuthenticate.visibility = View.INVISIBLE
        view!!.showSnackBar("Welcome ${user?.name}")
        updateController()
    }

    override fun onFailure(message: String) {
        butLogin.visibility = View.VISIBLE
        lavAuthenticate.visibility = View.GONE
        view!!.showSnackBar(message)
    }

    private fun updateController() {
        Intent(context, MainActivity::class.java).also {
            startActivity(
                it
            )
        }
    }
}

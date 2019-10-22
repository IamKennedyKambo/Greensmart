package org.triniti.greensmart.ui.login

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
import org.triniti.greensmart.data.db.databases.UserDatabase
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.network.LoginApi
import org.triniti.greensmart.data.network.NetworkConnectionInterceptor
import org.triniti.greensmart.data.repositories.LoginRepository
import org.triniti.greensmart.databinding.LayoutFLoginBinding
import org.triniti.greensmart.ui.login.interfaces.AuthResultCallback
import org.triniti.greensmart.ui.login.viewmodels.AuthViewModel
import org.triniti.greensmart.ui.login.viewmodels.AuthViewModelFactory
import org.triniti.greensmart.utilities.showSnackBar

class Signin : Fragment(), AuthResultCallback, KodeinAware {

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

        authViewModel.listener = this

        binding.viewModel = authViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivLogo.setImageResource(R.mipmap.ic_launcher)

        tvSignup.setOnClickListener {
            findNavController().navigate(R.id.action_destination_login_to_destination_signup)
        }
    }

    override fun onStarted(message: String) {
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
        findNavController().navigate(R.id.action_destination_login_to_destination_home)
    }
}

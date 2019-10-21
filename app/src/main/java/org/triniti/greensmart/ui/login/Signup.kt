package org.triniti.greensmart.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.layout_f_signup.*
import kotlinx.android.synthetic.main.layout_f_signup.ivLogo
import kotlinx.android.synthetic.main.layout_f_signup.lavAuthenticate
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.databases.UserDatabase
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.network.LoginApi
import org.triniti.greensmart.data.repositories.LoginRepository
import org.triniti.greensmart.databinding.LayoutFSignupBinding
import org.triniti.greensmart.ui.login.interfaces.AuthResultCallback
import org.triniti.greensmart.ui.login.viewmodels.AuthViewModel
import org.triniti.greensmart.ui.login.viewmodels.AuthViewModelFactory
import org.triniti.greensmart.utilities.showSnackBar
import org.triniti.greensmart.utilities.showToast

class Signup : Fragment(), AuthResultCallback {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LayoutFSignupBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_f_signup, container, false)

        val api = LoginApi()
        val db = UserDatabase(context!!)
        val repository = LoginRepository(api, db)

        binding.viewModel =
            ViewModelProviders.of(this, AuthViewModelFactory(this, repository)).get(AuthViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        ivLogo.setImageResource(R.mipmap.ic_launcher)

        tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_destination_signup_to_destination_login)
        }
    }

    override fun onStarted(message: String) {
        butCreate.visibility = View.INVISIBLE
        lavAuthenticate.visibility = View.VISIBLE
    }

    override fun onSuccess(user: User?) {
        butCreate.visibility = View.VISIBLE
        lavAuthenticate.visibility = View.INVISIBLE
        updateController()
        view!!.showSnackBar("Welcome ${user?.name}")
    }

    override fun onFailure(message: String) {
        butCreate.visibility = View.VISIBLE
        lavAuthenticate.visibility = View.GONE
        view!!.showSnackBar(message)
    }

    private fun updateController() {
        findNavController().navigate(R.id.action_destination_signup_to_destination_home)
    }
}
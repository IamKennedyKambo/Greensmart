package org.triniti.greensmart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.ui.auth.interfaces.AuthListener
import org.triniti.greensmart.ui.auth.viewmodels.AuthViewModel
import org.triniti.greensmart.ui.auth.viewmodels.AuthViewModelFactory
import org.triniti.greensmart.utilities.navigateUpOrFinish

class MainActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override fun onStarted() {

    }

    override fun onSuccess(user: User?) {

    }

    override fun onFailure(message: String) {

    }

    private lateinit var navController: NavController
    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_a_main)

        val authViewModel = ViewModelProviders.of(this, factory)
            .get(AuthViewModel::class.java)

        authViewModel.authListener = this

        navController = findNavController(this, R.id.container)

        authViewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user == null) {
                navController.navigate(R.id.destination_login)
            } else {
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUpOrFinish(this)
    }
}

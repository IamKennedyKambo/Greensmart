package org.triniti.greensmart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.databases.UserDatabase
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.network.LoginApi
import org.triniti.greensmart.data.repositories.LoginRepository
import org.triniti.greensmart.ui.login.interfaces.AuthResultCallback
import org.triniti.greensmart.ui.login.viewmodels.AuthViewModel
import org.triniti.greensmart.ui.login.viewmodels.AuthViewModelFactory
import org.triniti.greensmart.utilities.navigateUpOrFinish

class MainActivity : AppCompatActivity(), AuthResultCallback {
    override fun onStarted(message: String) {

    }

    override fun onSuccess(user: User?) {

    }

    override fun onFailure(message: String) {

    }

    private lateinit var navController: NavController
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_a_main)

        val api = LoginApi()
        val db = UserDatabase(this)
        val repository = LoginRepository(api, db)
        authViewModel = ViewModelProviders.of(this, AuthViewModelFactory(this, repository))
            .get(AuthViewModel::class.java)

        navController = findNavController(this, R.id.container)

        authViewModel.getCurrentUser().observe(this, Observer { user ->
//            if (user == null) {
//                navController.navigate(R.id.destination_login)
//            } else {
//                navController.navigate(R.id.destination_home)
//            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUpOrFinish(this)
    }
}

package org.triniti.greensmart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.triniti.greensmart.R
import org.triniti.greensmart.utilities.navigateUpOrFinish

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_a_main)

        navController = findNavController(R.id.fragHome)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUpOrFinish(this)
    }

    override fun onBackPressed() {
        navController.navigateUp() || navController.navigateUpOrFinish(this)
    }
}

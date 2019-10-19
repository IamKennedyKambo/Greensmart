package org.triniti.greensmart.utilities

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import org.triniti.greensmart.R

fun NavController.navigateUpOrFinish(activity: AppCompatActivity): Boolean {
    return if (navigateUp()) {
        true
    } else {
        activity.finish()
        true
    }
}

fun calculateNoOfColumns(
    context: Context,
    columnWidthDp: Float
): Int { // For example columnWidthdp=180
    val displayMetrics = context.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    return (screenWidthDp / columnWidthDp + 0.5).toInt()
}

fun Fragment.addMenu(toolbar: Toolbar) {
    toolbar.inflateMenu(R.menu.menu_cart)
    toolbar.setOnMenuItemClickListener {
        findNavController().navigate(R.id.destination_cart)
        true
    }
}

fun View.showSnackBar(text: CharSequence, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, text, length).show()
}

fun Context.showToast(text: CharSequence, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun View.toggleVisibility() {
    if (this.isVisible) {
        this.visibility == View.GONE
    } else {
        this.visibility == View.VISIBLE
    }
}
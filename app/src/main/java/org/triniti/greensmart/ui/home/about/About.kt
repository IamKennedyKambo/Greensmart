package org.triniti.greensmart.ui.home.about

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_d_confirmed.view.*
import kotlinx.android.synthetic.main.layout_f_about.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.triniti.greensmart.R
import org.triniti.greensmart.data.db.entities.Setting
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.databinding.LayoutFAboutBinding
import org.triniti.greensmart.ui.auth.Login
import org.triniti.greensmart.ui.home.complete.OnCompletionListener
import org.triniti.greensmart.utilities.getViewModel
import org.triniti.greensmart.utilities.showSnackBar

class About : Fragment(), KodeinAware, OnCompletionListener, AboutItem.OnLogOutRequest {

    override val kodein by kodein()

    private lateinit var aboutViewModel: AboutViewModel
    private val factory: AboutViewModelFactory by instance()
    private lateinit var binding: LayoutFAboutBinding

    private val settings: MutableList<AboutItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.layout_f_about, container, false)
        aboutViewModel = activity?.getViewModel(factory)!!
        binding.viewModel = aboutViewModel
        aboutViewModel.completionListener = this
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvAbout.setOnEditorActionListener { textView, _, _ ->
            val user = aboutViewModel.user.value
            val about = textView.text.toString()
            if (about.isNotEmpty() && about.isNotBlank() && about != getString(R.string.tell_us_more)) {
                val newUser = user?.copy(about = textView.text.toString())
                aboutViewModel.updateUser(newUser!!)
            }
            true
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(
                getSettings()
            )
        }

        rvSetting.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = groupAdapter
        }
    }

    private fun getSettings(): List<AboutItem> {
        settings.add(
            AboutItem(
                Setting(
                    "Like our page",
                    context!!.getDrawable(R.drawable.vector_facebook)!!
                ), this
            )
        )

        settings.add(
            AboutItem(
                Setting(
                    "Follow us",
                    context!!.getDrawable(R.drawable.vector_twitter)!!
                ), this
            )
        )
        settings.add(
            AboutItem(
                Setting(
                    "Sponsors",
                    context!!.getDrawable(R.drawable.vector_sponsors)!!
                ), this
            )
        )
        settings.add(
            AboutItem(
                Setting(
                    "About us",
                    context!!.getDrawable(R.drawable.vector_about)!!
                ), this
            )
        )

        settings.add(
            AboutItem(
                Setting(
                    "log out",
                    context!!.getDrawable(R.drawable.vector_logout)!!
                ), this
            )
        )

        return settings
    }

    override fun onCompletion(user: User) {
        view?.showSnackBar("Updated successfully")
    }

    override fun onFailure(message: String) {
        view?.showSnackBar(message)
    }

    override fun requestLogout(request: Boolean) {
        if (request)
            showConfirm()
    }

    private fun showConfirm() {
        MaterialDialog(requireContext()).show {
            customView(R.layout.layout_d_confirmed)
            val view = getCustomView()
            val confirm = view.butConfirm
            val cancel = view.butCancel
            val heading = view.tvWarning
            val content = view.tvMessage

            content.text = getString(R.string.logout_title)
            heading.text = getString(R.string.logout_message)

            confirm.setOnClickListener {
                aboutViewModel.logOut()
                val intent = Intent(view.context, Login::class.java)

                intent.also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    view.context.startActivity(it)
                }
                dismiss()
            }

            cancel.setOnClickListener {
                dismiss()
            }
        }
    }
}
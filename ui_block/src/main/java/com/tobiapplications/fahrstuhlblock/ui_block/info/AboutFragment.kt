package com.tobiapplications.fahrstuhlblock.ui_block.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.info.AboutViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.BR
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.FragmentAboutBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : BaseToolbarFragment<AboutViewModel, BlockViewModel, FragmentAboutBinding>() {

    override val activityToolbarViewModel: BlockViewModel by sharedViewModel()
    override val viewModel: AboutViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_about
    override val viewModelResId: Int = BR.viewModel

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(R.string.about_toolbar_title))

        viewModel.sendEmail.observe(viewLifecycleOwner, Observer {
            sendEmail()
        })
        viewModel.openFahrstuhl.observe(viewLifecycleOwner, Observer {
            openFahrstuhl()
        })
        viewModel.openWizard.observe(viewLifecycleOwner, Observer {
            openWizard()
        })
        viewModel.openMovieBase.observe(viewLifecycleOwner, Observer {
            openMoviebase()
        })
    }

    private fun openFahrstuhl() {
        openInPlayStore(requireContext().packageName)
    }

    private fun openWizard() {
        openInPlayStore(getString(R.string.about_package_name_wizard_block))
    }

    private fun openMoviebase() {
        openInPlayStore(getString(R.string.about_package_name_moviebase))
    }

    private fun openInPlayStore(packageName: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_playstore_prefix, packageName))))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_playstore_url, packageName))))
        }
    }

    private fun sendEmail() {
        val text = getString(R.string.about_email_text)
        val email = getString(R.string.about_email)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }

        val mailer = Intent.createChooser(intent, getString(R.string.about_email_chooser_title))
        try {
            startActivity(mailer)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                getString(R.string.about_no_email_clients),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

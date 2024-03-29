package com.tobiapplications.fahrstuhlblock.ui_block.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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

        activityToolbarViewModel.setTitle(getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.about_toolbar_title))

        viewModel.sendEmail.observe(viewLifecycleOwner, {
            sendEmail()
        })
        viewModel.openFahrstuhl.observe(viewLifecycleOwner, {
            openFahrstuhl()
        })
        viewModel.openWizard.observe(viewLifecycleOwner, {
            openWizard()
        })
        viewModel.openMovieBase.observe(viewLifecycleOwner, {
            openMoviebase()
        })
    }

    private fun openFahrstuhl() {
        openInPlayStore(requireContext().packageName)
    }

    private fun openWizard() {
        openInPlayStore(getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.about_package_name_wizard_block))
    }

    private fun openMoviebase() {
        openInPlayStore(getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.about_package_name_moviebase))
    }

    private fun openInPlayStore(packageName: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.about_playstore_prefix, packageName))))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.about_playstore_url, packageName))))
        }
    }

    private fun sendEmail() {
        val text = getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.about_email_text)
        val email = getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.about_email)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_SUBJECT, getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.app_name))
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }

        val mailer = Intent.createChooser(intent, getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.about_email_chooser_title))
        try {
            startActivity(mailer)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.about_no_email_clients),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

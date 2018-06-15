package com.samizerouta.definitelytypedforks.ui.repodetail

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import com.samizerouta.definitelytypedforks.R
import com.samizerouta.definitelytypedforks.entity.Repo
import com.samizerouta.definitelytypedforks.util.loadOwnerAvatar
import kotlinx.android.synthetic.main.fragment_repo_detail.*
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*

class RepoDetailDialogFragment : DialogFragment() {
  companion object {
    private const val ARG_REPO = "ARG_REPO"

    fun newInstance(repo: Repo) = RepoDetailDialogFragment().apply {
      arguments = bundleOf(ARG_REPO to repo)
    }
  }

  private val repo by lazy(LazyThreadSafetyMode.NONE) {
    arguments!!.getParcelable<Repo>(ARG_REPO)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_repo_detail, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    if (showsDialog) {
      dialog.run {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(false)
      }
    }

    toolbar.setNavigationOnClickListener { if (showsDialog) dismiss() else requireActivity().onBackPressed() }

    toolbar.title = repo.fullName
    ownerAvatar.loadOwnerAvatar(repo)
    description.text = repo.description

    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
      .withLocale(Locale.US)
      .withZone(ZoneId.systemDefault())

    createdAt.text = getString(R.string.created_on, formatter.format(repo.createdAt))
  }
}
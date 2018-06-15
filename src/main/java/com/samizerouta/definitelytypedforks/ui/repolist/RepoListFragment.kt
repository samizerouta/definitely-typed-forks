package com.samizerouta.definitelytypedforks.ui.repolist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.samizerouta.definitelytypedforks.R
import com.samizerouta.definitelytypedforks.ui.repodetail.RepoDetailDialogFragment
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_repo_list.*
import javax.inject.Inject

class RepoListFragment : DaggerFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val repoListViewModel by lazy(LazyThreadSafetyMode.NONE) {
    ViewModelProviders.of(this, viewModelFactory)[RepoListViewModel::class.java]
  }

  private var disposable: Disposable? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (savedInstanceState == null) {
      loadRepos()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_repo_list, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val repoAdapter = RepoAdapter {
      val fragment = RepoDetailDialogFragment.newInstance(it)

      if (resources.getBoolean(R.bool.is_tablet)) {
        fragment.show(requireFragmentManager(), null)
      } else {
        requireFragmentManager()
          .beginTransaction()
          .replace(android.R.id.content, fragment)
          .addToBackStack(null)
          .commit()
      }
    }

    disposable = repoListViewModel.models.subscribe {
      when (it) {
        RepoListViewModel.Model.Loading -> {
          repos.isVisible = false
          retry.isVisible = false
          progress.isVisible = true
        }
        is RepoListViewModel.Model.Failure -> {
          repos.isVisible = false
          progress.isVisible = false
          retry.isVisible = true
        }
        is RepoListViewModel.Model.Success -> {
          repoAdapter.repos = it.repos
          progress.isVisible = false
          retry.isVisible = false
          repos.isVisible = true
        }
      }
    }

    val linearLayoutManager = LinearLayoutManager(context)

    with(repos) {
      setHasFixedSize(true)
      adapter = repoAdapter
      layoutManager = linearLayoutManager
      addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
    }

    retry.setOnClickListener { loadRepos() }
  }

  override fun onDestroyView() {
    disposable?.dispose()
    super.onDestroyView()
  }

  private fun loadRepos() {
    repoListViewModel.events.onNext(
      RepoListViewModel.LoadForksEvent(
        "DefinitelyTyped",
        "DefinitelyTyped"
      )
    )
  }
}
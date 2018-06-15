package com.samizerouta.definitelytypedforks.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.samizerouta.definitelytypedforks.ui.ViewModelFactory
import com.samizerouta.definitelytypedforks.ui.repolist.RepoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(RepoListViewModel::class)
  abstract fun bindRepoListViewModel(viewModel: RepoListViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
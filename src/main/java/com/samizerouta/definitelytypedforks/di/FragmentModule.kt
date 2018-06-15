package com.samizerouta.definitelytypedforks.di

import com.samizerouta.definitelytypedforks.ui.repolist.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
abstract class FragmentModule {
  @ContributesAndroidInjector
  abstract fun contributeRepositoryListFragment(): RepoListFragment
}
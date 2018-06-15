package com.samizerouta.definitelytypedforks.di

import com.samizerouta.definitelytypedforks.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
abstract class MainActivityModule {
  @ContributesAndroidInjector(modules = [FragmentModule::class])
  abstract fun contributeMainActivity(): MainActivity
}
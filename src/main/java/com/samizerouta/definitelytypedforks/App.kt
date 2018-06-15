package com.samizerouta.definitelytypedforks

import android.app.Activity
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.samizerouta.definitelytypedforks.di.AppComponent
import com.samizerouta.definitelytypedforks.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {
  @Inject
  lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

  lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()

    AndroidThreeTen.init(this)

    appComponent = DaggerAppComponent.builder()
      .application(this)
      .build()

    appComponent.inject(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}
package com.samizerouta.definitelytypedforks.di

import android.app.Application
import com.samizerouta.definitelytypedforks.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    MainActivityModule::class,
    ViewModelModule::class
  ]
)
interface AppComponent {
  val client: OkHttpClient

  fun inject(app: App)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }
}
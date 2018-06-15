package com.samizerouta.definitelytypedforks.di

import android.app.Application
import com.samizerouta.definitelytypedforks.R
import com.samizerouta.definitelytypedforks.data.GitHubService
import com.samizerouta.definitelytypedforks.util.DefaultSchedulerProvider
import com.samizerouta.definitelytypedforks.util.InstantMoshiAdapter
import com.samizerouta.definitelytypedforks.util.SchedulerProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object AppModule {
  @Singleton
  @Provides
  @JvmStatic
  fun provideClient(
    application: Application
  ): OkHttpClient {
    val appName = application.resources.getString(R.string.app_name)

    return OkHttpClient.Builder()
      .addInterceptor { chain ->
        chain.proceed(
          chain.request()
            .newBuilder()
            .header("User-Agent", appName)
            .build()
        )
      }
      .addInterceptor(
        HttpLoggingInterceptor()
          .setLevel(HttpLoggingInterceptor.Level.BASIC)
      )
      .build()
  }

  @Singleton
  @Provides
  @JvmStatic
  fun provideGitHubService(
    client: OkHttpClient
  ): GitHubService = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .client(client)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(
      MoshiConverterFactory.create(
        Moshi.Builder()
          .add(InstantMoshiAdapter())
          .build()
      )
    )
    .build()
    .create(GitHubService::class.java)

  @Provides
  @JvmStatic
  fun provideSchedulerProvider(): SchedulerProvider = DefaultSchedulerProvider
}
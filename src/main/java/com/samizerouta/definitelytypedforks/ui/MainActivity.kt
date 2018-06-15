package com.samizerouta.definitelytypedforks.ui

import android.os.Bundle
import com.samizerouta.definitelytypedforks.ui.repolist.RepoListFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(android.R.id.content, RepoListFragment())
        .commit()
    }
  }
}
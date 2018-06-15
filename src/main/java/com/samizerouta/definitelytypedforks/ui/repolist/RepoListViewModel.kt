package com.samizerouta.definitelytypedforks.ui.repolist

import android.arch.lifecycle.ViewModel
import com.samizerouta.definitelytypedforks.data.GitHubService
import com.samizerouta.definitelytypedforks.entity.Repo
import com.samizerouta.definitelytypedforks.util.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
  private val service: GitHubService,
  private val schedulerProvider: SchedulerProvider
) : ViewModel() {
  private val _models = BehaviorSubject.create<Model>()
  val models: Observable<Model> get() = _models

  private val _events = PublishSubject.create<LoadForksEvent>()
  val events: Observer<LoadForksEvent> get() = _events

  private var disposable: Disposable? = null

  init {
    disposable = _events.switchMap { (owner, repo) ->
      service.forks(owner, repo)
        .subscribeOn(schedulerProvider.io)
        .map<Model>(Model::Success)
        .onErrorReturn(Model::Failure)
        .toObservable()
        .observeOn(schedulerProvider.ui)
        .startWith(Model.Loading)
    }.subscribe(_models::onNext)
  }

  override fun onCleared() {
    disposable?.dispose()
  }

  data class LoadForksEvent(val owner: String, val repo: String)

  sealed class Model {
    data class Success(val repos: List<Repo>) : Model()
    data class Failure(val error: Throwable) : Model()
    object Loading : Model()
  }
}
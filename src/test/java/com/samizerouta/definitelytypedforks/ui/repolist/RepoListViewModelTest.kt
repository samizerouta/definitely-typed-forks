package com.samizerouta.definitelytypedforks.ui.repolist

import com.samizerouta.definitelytypedforks.data.GitHubService
import com.samizerouta.definitelytypedforks.entity.Repo
import com.samizerouta.definitelytypedforks.entity.User
import com.samizerouta.definitelytypedforks.util.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Test
import org.threeten.bp.Instant
import java.io.IOException

class RepoListViewModelTest {
  @Test
  fun loadForks_shouldForwardParams() {
    val vm = RepoListViewModel(
      GitHubService { owner, repo ->
        Assert.assertEquals("square", owner)
        Assert.assertEquals("retrofit", repo)

        Single.just(emptyList())
      },
      TestSchedulerProvider()
    )

    vm.events.onNext(RepoListViewModel.LoadForksEvent("square", "retrofit"))
  }

  @Test
  fun loadForks_shouldNotifyLoadingAndSuccess_whenServiceSucceed() {
    val repos = listOf(
      Repo(
        "fullName",
        User("avatarUrl"),
        "description",
        Instant.EPOCH
      )
    )

    val vm = RepoListViewModel(
      GitHubService { _, _ -> Single.just(repos) },
      TestSchedulerProvider()
    )

    val o = vm.models.test()
    vm.events.onNext(RepoListViewModel.LoadForksEvent("square", "retrofit"))

    o.assertValueSequenceOnly(
      listOf(
        RepoListViewModel.Model.Loading,
        RepoListViewModel.Model.Success(repos)
      )
    )
  }

  @Test
  fun loadForks_shouldNotifyLoadingAndFailure_whenServiceFailed() {
    val error = IOException()

    val vm = RepoListViewModel(
      GitHubService { _, _ -> Single.error(error) },
      TestSchedulerProvider()
    )

    val o = vm.models.test()
    vm.events.onNext(RepoListViewModel.LoadForksEvent("square", "retrofit"))

    o.assertValueSequenceOnly(
      listOf(
        RepoListViewModel.Model.Loading,
        RepoListViewModel.Model.Failure(error)
      )
    )
  }

  inline fun GitHubService(crossinline forks: (owner: String, repo: String) -> Single<List<Repo>>) =
    object : GitHubService {
      override fun forks(owner: String, repo: String): Single<List<Repo>> = forks(owner, repo)
    }

  private fun TestSchedulerProvider(
    io: Scheduler = Schedulers.trampoline(),
    ui: Scheduler = Schedulers.trampoline()
  ) = object : SchedulerProvider {
    override val io: Scheduler get() = io
    override val ui: Scheduler get() = ui
  }
}
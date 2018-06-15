package com.samizerouta.definitelytypedforks.data

import com.samizerouta.definitelytypedforks.entity.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GitHubService {
  @Headers("Accept: application/vnd.github.v3+json")
  @GET("repos/{owner}/{repo}/forks")
  fun forks(
    @Path("owner") owner: String,
    @Path("repo") repo: String
  ): Single<List<Repo>>
}
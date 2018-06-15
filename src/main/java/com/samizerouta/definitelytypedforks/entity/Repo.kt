package com.samizerouta.definitelytypedforks.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Instant

@Parcelize
@JsonClass(generateAdapter = true)
data class Repo(
  @Json(name = "full_name") val fullName: String,
  val owner: User,
  val description: String,
  @Json(name = "created_at") val createdAt: Instant
) : Parcelable
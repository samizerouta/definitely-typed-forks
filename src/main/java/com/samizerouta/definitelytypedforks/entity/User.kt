package com.samizerouta.definitelytypedforks.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
  @Json(name = "avatar_url") val avatarUrl: String
) : Parcelable
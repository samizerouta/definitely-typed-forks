package com.samizerouta.definitelytypedforks.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.Instant

class InstantMoshiAdapter {
  @ToJson
  fun toJson(instant: Instant): String = instant.toString()

  @FromJson
  fun fromJson(s: String): Instant = Instant.parse(s)
}
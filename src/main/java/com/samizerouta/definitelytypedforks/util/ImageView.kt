package com.samizerouta.definitelytypedforks.util

import android.widget.ImageView
import com.samizerouta.definitelytypedforks.R
import com.samizerouta.definitelytypedforks.entity.Repo
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

fun ImageView.loadOwnerAvatar(repo: Repo) = GlideApp.with(this)
  .load(repo.owner.avatarUrl)
  .placeholder(R.drawable.placeholder_avatar)
  .transform(
    RoundedCornersTransformation(
      resources.getDimensionPixelSize(R.dimen.avatar_corner_radius),
      0
    )
  )
  .into(this)
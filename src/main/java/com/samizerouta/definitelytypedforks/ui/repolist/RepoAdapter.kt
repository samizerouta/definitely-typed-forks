package com.samizerouta.definitelytypedforks.ui.repolist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samizerouta.definitelytypedforks.R
import com.samizerouta.definitelytypedforks.entity.Repo
import com.samizerouta.definitelytypedforks.util.loadOwnerAvatar
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter(
  private val onClick: (Repo) -> Unit
) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {
  var repos: List<Repo> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun getItemCount() = repos.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val itemView = inflater.inflate(R.layout.item_repo, parent, false)
    return ViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val repo = repos[position]

    with(holder.itemView) {
      fullName.text = repo.fullName
      ownerAvatar.loadOwnerAvatar(repo)
      setOnClickListener { onClick(repo) }
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
package com.mjandroiddev.nykaalibrary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mjandroiddev.nykaalibrary.R
import com.mjandroiddev.nykaalibrary.db.entities.Session
/*
* SessionHistoryAdapter
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
class SessionHistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val asyncList = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Session>() {
        override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem == newItem
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_session_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HistoryVH) {
            holder.bind(asyncList.currentList[position])
        }
    }

    override fun getItemCount() = asyncList.currentList.size

}
package com.mjandroiddev.nykaalibrary.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mjandroiddev.nykaalibrary.databinding.ItemSessionDetailBinding
import com.mjandroiddev.nykaalibrary.db.entities.Session
import com.mjandroiddev.nykaalibrary.utils.getTimeString
import com.mjandroiddev.nykaalibrary.utils.toDate
import com.mjandroiddev.nykaalibrary.utils.toTime
import java.util.concurrent.TimeUnit

/*
* HistoryVH
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
class HistoryVH(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemSessionDetailBinding.bind(view)

    fun bind(session: Session) {
        binding.tvIn.text = session.inTime?.toTime()
        binding.tvOut.text = session.outTime?.toTime()
        binding.tvDate.text = session.date?.toDate()
        val perSessionTime = (session.assignSessionTime ?: 0) - (session.pendingSessionTime ?: 0)
        binding.tvSession.text = getTimeString(perSessionTime)
    }
}
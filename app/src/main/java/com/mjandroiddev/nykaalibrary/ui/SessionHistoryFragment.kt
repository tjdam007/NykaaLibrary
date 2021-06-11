package com.mjandroiddev.nykaalibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mjandroiddev.nykaalibrary.R
import com.mjandroiddev.nykaalibrary.databinding.FragmentSessionHistoryBinding
import com.mjandroiddev.nykaalibrary.utils.getFullTimeString
import com.mjandroiddev.nykaalibrary.utils.getTimeString
import com.mjandroiddev.nykaalibrary.viewmodels.SessionViewModel
import kotlinx.coroutines.flow.collect

/*
* SessionHistoryFragment uses for show all the old session.
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
class SessionHistoryFragment : Fragment() {

    private lateinit var binding: FragmentSessionHistoryBinding
    private lateinit var adapter: SessionHistoryAdapter
    private val sessionViewModel: SessionViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        adapter = SessionHistoryAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSessionHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter

        // Setup the adapter
        lifecycleScope.launchWhenStarted {
            //Get the data from DB
            sessionViewModel.getHistorySession().collect { sessions ->
                adapter.asyncList.submitList(sessions)
                binding.tvTotalSessionTime.text = getString(
                    R.string.total_session_time,
                    getFullTimeString(sessions.sumOf { session ->
                        (session.assignSessionTime ?: 0) - (session.pendingSessionTime ?: 0)
                    })
                )
            }
        }
    }
}
package com.mjandroiddev.nykaalibrary.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mjandroiddev.nykaalibrary.R
import com.mjandroiddev.nykaalibrary.databinding.FragmentHomeBinding
import com.mjandroiddev.nykaalibrary.utils.Constants
import com.mjandroiddev.nykaalibrary.utils.getTimeString
import com.mjandroiddev.nykaalibrary.utils.navigateSafe
import com.mjandroiddev.nykaalibrary.viewmodels.SessionViewModel
import com.mjandroiddev.nykaalibrary.workManagers.SessionWorker
import kotlinx.coroutines.flow.collect
import java.util.*
import java.util.concurrent.TimeUnit

/*
* HomeFragment is used for starting/stopping a session for the user
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val sessionVM: SessionViewModel by activityViewModels()
    private val workManager: WorkManager by lazy {
        WorkManager.getInstance(requireContext().applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            //Check Previous Session is active or not
            sessionVM.getActiveSession().collect { activeSession ->
                //Setup strings & timer
                if (activeSession?.isActive == true) {
                    binding.tvSessionTimer.text = getString(
                        R.string.time_remaining,
                        getTimeString(activeSession.pendingSessionTime ?: 0L)
                    )
                    binding.buttonSession.text = getString(R.string.exit_from_library)
                } else {
                    binding.tvSessionTimer.text = getString(R.string.click_button_to_start)
                    binding.buttonSession.text = getString(R.string.enter_to_library)
                }
                //set up click
                binding.buttonSession.setOnClickListener {
                    if (activeSession?.isActive == true) {
                        // Close Session
                        activeSession.isActive = false
                        activeSession.outTime = Date().time
                        sessionVM.insertOrUpdateSession(activeSession)
                        // Remove Notification
                        workManager.cancelUniqueWork(Constants.SESSION_WORKER)
                    } else {
                        // Start Session
                        sessionVM.startSession()
                        //Show Notification with timer
                        val task = OneTimeWorkRequestBuilder<SessionWorker>().build()
                        workManager.beginUniqueWork(
                            Constants.SESSION_WORKER,
                            ExistingWorkPolicy.REPLACE,
                            task
                        ).enqueue()
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sessionHistory -> openHistory()
        }
        return super.onOptionsItemSelected(item)
    }

    /*
    * Open old Session History
    * */
    private fun openHistory() {
        findNavController().navigateSafe(
            R.id.homeFragment,
            R.id.action_homeFragment_to_sessionHistoryFragment
        )
    }
}
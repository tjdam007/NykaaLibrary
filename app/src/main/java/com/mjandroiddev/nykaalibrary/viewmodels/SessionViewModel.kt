package com.mjandroiddev.nykaalibrary.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mjandroiddev.nykaalibrary.db.entities.Session
import com.mjandroiddev.nykaalibrary.repositories.SessionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * SessionViewModel
 * Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
 * */
class SessionViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = SessionRepo(app)

    /*
    * Start a session
    * */
    fun startSession(): MutableLiveData<Session> {
        val liveData = MutableLiveData<Session>()
        val sessionTime = when (Random.nextInt(0, 3)) {
            0 -> 20L
            1 -> 30L
            2 -> 45L
            else -> 60L
        }
        val date = Date()
        val session = Session().apply {
            inTime = date.time
            assignSessionTime = TimeUnit.MINUTES.toMillis(sessionTime)
            pendingSessionTime = TimeUnit.MINUTES.toMillis(sessionTime)
        }
        viewModelScope.launch(Dispatchers.IO) {
            session.id = repository.insertOrUpdate(session)
            liveData.postValue(session)
        }
        return liveData
    }

    /*
    * Get Active Session
    * */
    fun getActiveSession() = repository.getActiveSession()


    /*
    * Insert or Update a session
    * */
    fun insertOrUpdateSession(session: Session) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertOrUpdate(session)
        }
    }

    /*
    * Get All session
    * */
    fun getHistorySession() = repository.getHistorySession()
}
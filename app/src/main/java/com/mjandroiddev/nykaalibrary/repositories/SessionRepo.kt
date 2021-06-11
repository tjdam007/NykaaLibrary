package com.mjandroiddev.nykaalibrary.repositories

import android.app.Application
import com.mjandroiddev.nykaalibrary.db.Database
import com.mjandroiddev.nykaalibrary.db.entities.Session

/*
* SessionRepo
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
class SessionRepo(app: Application) {
    private val sessionDao = Database.getDatabase(app).sessionDao()

    /*
    * Insert or Update Session
    * */
    suspend fun insertOrUpdate(session: Session?) = sessionDao.insertOrUpdate(session)

    /*
    * Get Active Session
    * */
    fun getActiveSession() = sessionDao.getActiveSession()

    /*
    * Get all history session
    * */
    fun getHistorySession() = sessionDao.getHistorySession()
}
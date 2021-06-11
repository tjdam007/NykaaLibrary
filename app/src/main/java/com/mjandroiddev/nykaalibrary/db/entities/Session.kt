package com.mjandroiddev.nykaalibrary.db.entities

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
* Session Model for storing data
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
@Entity(tableName = "sessions")
class Session {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var assignSessionTime: Long? = null
    var pendingSessionTime: Long? = null
    var inTime: Long? = null
    var outTime: Long? = null
    var date: Long? = null
    var isActive: Boolean = true

    override fun toString(): String {
        return "Session(id=$id, assignSessionTime=$assignSessionTime, inTime=$inTime, outTime=$outTime, date=$date, isActive=$isActive)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Session

        if (id != other.id) return false
        if (assignSessionTime != other.assignSessionTime) return false
        if (pendingSessionTime != other.pendingSessionTime) return false
        if (inTime != other.inTime) return false
        if (outTime != other.outTime) return false
        if (date != other.date) return false
        if (isActive != other.isActive) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (assignSessionTime?.hashCode() ?: 0)
        result = 31 * result + (pendingSessionTime?.hashCode() ?: 0)
        result = 31 * result + (inTime?.hashCode() ?: 0)
        result = 31 * result + (outTime?.hashCode() ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + isActive.hashCode()
        return result
    }


}


/*
* DAO interface for accessing data from database
* */
@Dao
interface SessionDao {

    /*
    * Insert or update Session
    * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(session: Session?): Long

    /*
    * get all session
    * */
    @Query("Select * from sessions order by date desc")
    fun getHistorySession(): Flow<List<Session>>

    /*
    * Get Active Session
    * */
    @Query("Select * from sessions where isActive=1")
    fun getActiveSession(): Flow<Session?>
}
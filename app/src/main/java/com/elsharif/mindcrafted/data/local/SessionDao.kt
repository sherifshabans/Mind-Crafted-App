package com.elsharif.mindcrafted.data.local

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.elsharif.mindcrafted.domain.model.Session
import com.elsharif.mindcrafted.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface SessionDao {

    @Upsert
    suspend fun insertSession(session: Session)

    @Delete
    suspend fun deleteSession(session:Session)


    @Query("SELECT  *FROM Session ")
    suspend fun getAllSessions(): Flow<List<Session>>

    @Query("SELECT  *FROM Session WHERE  sessionSubjectId =:subjectId")
    fun getRecentSessionForSubject(subjectId:Int):Flow<List<Int>>

    @Query("SELECT SUM(duration) FROM Session")
    fun getTotalSessionDuration():Flow<Long>

    @Query("SELECT SUM(duration) FROM Session WHERE sessionSubjectId =:subjectId")
    fun getTotalSessionDurationBySubjectId(subjectId: Int):Flow<Long>

    @Query("DELETE FROM Session WHERE sessionSubjectId =:subjectId")
    fun deleteSessionsBySubjectId(subjectId: Int)

}
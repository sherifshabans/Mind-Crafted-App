package com.elsharif.mindcrafted.data.repository

import com.elsharif.mindcrafted.data.local.SubjectDao
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao
):SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {

        subjectDao.upsertSubject(subject)
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        return subjectDao.getTotalSubjectCount()
    }

    override fun getTotalGoalHours(): Flow<Float> {
        return subjectDao.getTotalGoalHours()
    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSubject(subjectId: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllSubjects(): Flow<List<Subject>> {
        return subjectDao.getAllSubjects()
    }
}
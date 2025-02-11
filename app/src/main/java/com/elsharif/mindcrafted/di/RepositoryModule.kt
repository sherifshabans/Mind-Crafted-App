package com.elsharif.mindcrafted.di

import com.elsharif.mindcrafted.data.repository.SessionRepositoryImpl
import com.elsharif.mindcrafted.data.repository.SubjectRepositoryImpl
import com.elsharif.mindcrafted.data.repository.TaskRepositoryImpl
import com.elsharif.mindcrafted.domain.repository.SessionRepository
import com.elsharif.mindcrafted.domain.repository.SubjectRepository
import com.elsharif.mindcrafted.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl:SubjectRepositoryImpl
    ):SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl:TaskRepositoryImpl
    ):TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl:SessionRepositoryImpl
    ):SessionRepository



}
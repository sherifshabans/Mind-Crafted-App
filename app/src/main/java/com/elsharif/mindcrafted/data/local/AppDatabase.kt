package com.elsharif.mindcrafted.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elsharif.mindcrafted.domain.model.Session
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.domain.model.Task


@Database(
    entities = [Subject::class, Session::class, Task::class],
    version = 1
)
@TypeConverters(ColorListConverter::class)
abstract class AppDatabase :RoomDatabase(){

    abstract fun subjectDao():SubjectDao

    abstract fun taskDao():TaskDao

    abstract fun sessionDao():SessionDao

}
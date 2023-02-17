package com.geserrato.todoapp.addtask.data.di

import android.content.Context
import androidx.room.Room
import com.geserrato.todoapp.addtask.data.TaskDAO
import com.geserrato.todoapp.addtask.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): TodoDatabase {
        return Room.databaseBuilder(appContext, TodoDatabase::class.java, "TaskDatabase").build()
    }

    @Provides
    fun provideTaskDao(todoDatabase: TodoDatabase): TaskDAO {
        return todoDatabase.taskDao()
    }
}
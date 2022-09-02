package com.scitechstudios.todo2.di

import android.app.Application
import androidx.room.Room
import com.scitechstudios.todo2.data.TodoRepository
import com.scitechstudios.todo2.data.Tododatabase
import com.scitechstudios.todo2.data.TodorRepoImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Appmodule {

    @Provides
    @Singleton
    fun providedatabase(app: Application): Tododatabase {
        return Room.databaseBuilder(
            app,
            Tododatabase::class.java,
            "todo_db"
        ).build()
    }
    @Provides
    @Singleton
    fun providerepo(db:Tododatabase): TodoRepository {
        return TodorRepoImplementation(db.dao)
    }
}
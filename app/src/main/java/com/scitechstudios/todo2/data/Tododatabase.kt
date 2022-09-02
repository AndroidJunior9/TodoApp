package com.scitechstudios.todo2.data

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [ToDo::class],
    version = 1
)
abstract class Tododatabase: RoomDatabase(){
    abstract val dao: Tododao
}
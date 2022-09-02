package com.scitechstudios.todo2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    val title:String,
    val description:String?,
    val isdone:Boolean,
    @PrimaryKey val id:Int?=null

)

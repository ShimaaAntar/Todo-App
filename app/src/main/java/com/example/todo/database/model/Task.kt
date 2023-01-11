package com.example.todo.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Task(
    @ColumnInfo @PrimaryKey(autoGenerate = true) val id:Int?=null,
    @ColumnInfo val title:String?=null,
    @ColumnInfo val description:String?=null,
    @ColumnInfo val isCompleted:Boolean?=false
)
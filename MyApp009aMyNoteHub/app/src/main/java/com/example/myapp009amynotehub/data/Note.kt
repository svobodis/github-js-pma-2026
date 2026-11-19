package com.example.myapp009amynotehub.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // ID poznámky, automaticky generované
    val title: String, // Název poznámky
    val content: String,
    val priority: String = "Střední",

    val createdAt: Long = System.currentTimeMillis(),
    val category: String = "Obecné",
    val color: String = "yellow"
)

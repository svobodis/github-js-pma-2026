package com.example.myapp009amynotehub.data

import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

@Database(
    entities = [Note::class],
    version = 3,
    exportSchema = false
)

abstract class NoteHubDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE note_table ADD COLUMN priority TEXT NOT NULL DEFAULT 'Střední'")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE note_table ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0")
        connection.execSQL("ALTER TABLE note_table ADD COLUMN category TEXT NOT NULL DEFAULT 'Obecné'")
        connection.execSQL("ALTER TABLE note_table ADD COLUMN color TEXT NOT NULL DEFAULT 'yellow'")
    }
}

package ru.l4gunner4l.contactsapp.contacts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao

    companion object {
        const val CONTACTS_TABLE = "CONTACTS_TABLE"
    }
}
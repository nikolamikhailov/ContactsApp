package ru.l4gunner4l.contactsapp.di.dagger.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.l4gunner4l.contactsapp.contacts.data.local.ContactsDao
import ru.l4gunner4l.contactsapp.contacts.data.local.ContactsDatabase
import ru.l4gunner4l.contactsapp.contacts.data.local.ContactsDatabase.Companion.CONTACTS_TABLE
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun providesRoom(context: Context): ContactsDatabase {
        return Room.databaseBuilder(
            context,
            ContactsDatabase::class.java,
            CONTACTS_TABLE
        ).build()
    }

    @Provides
    @Singleton
    fun providesDao(db: ContactsDatabase): ContactsDao {
        return db.contactsDao()
    }
}
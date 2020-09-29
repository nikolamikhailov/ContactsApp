package ru.l4gunner4l.contactsapp.contacts.data.local

import androidx.room.*
import io.reactivex.Single
import ru.l4gunner4l.contactsapp.base.CONTACTS_TABLE

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(entity: ContactEntity): Single<Unit>

    @Query("SELECT * FROM $CONTACTS_TABLE")
    fun read(): Single<List<ContactEntity>>

    @Query("SELECT * FROM $CONTACTS_TABLE WHERE _id = :id")
    fun readSingle(id: String): Single<ContactEntity>

    @Update
    fun update(entity: ContactEntity): Single<Unit>

    @Delete
    fun delete(entity: ContactEntity): Single<Unit>
}
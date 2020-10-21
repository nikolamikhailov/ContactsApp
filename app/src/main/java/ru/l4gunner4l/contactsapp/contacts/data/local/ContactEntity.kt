package ru.l4gunner4l.contactsapp.contacts.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.l4gunner4l.contactsapp.base.CONTACTS_TABLE

@Entity(tableName = CONTACTS_TABLE)
data class ContactEntity(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: String,
    @ColumnInfo(name = "imagePath")
    var imagePath: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "phone")
    var phone: String
)
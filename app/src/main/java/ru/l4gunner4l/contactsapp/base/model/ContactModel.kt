package ru.l4gunner4l.contactsapp.base.model

import java.util.*

data class ContactModel(
    val id: String = UUID.randomUUID().toString(),
    var imagePath: String,
    var name: String,
    var phone: String
)
package ru.l4gunner4l.contactsapp.base

import ru.l4gunner4l.contactsapp.base.model.ContactModel
import ru.l4gunner4l.contactsapp.contacts.data.local.ContactEntity

fun ContactEntity.mapToUiModel(): ContactModel {
    return ContactModel(id, imagePath, name, phone)
}

fun ContactModel.mapToEntityModel(): ContactEntity {
    return ContactEntity(id, imagePath, name, phone)
}
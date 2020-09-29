package ru.l4gunner4l.contactsapp.contacts.data

import io.reactivex.Single
import ru.l4gunner4l.contactsapp.base.model.ContactModel

interface ContactsRepository {

    fun getContacts(): Single<List<ContactModel>>

    fun deleteContact(entity: ContactModel): Single<Unit>
}
package ru.l4gunner4l.contactsapp.details.data

import io.reactivex.Single
import ru.l4gunner4l.contactsapp.base.model.ContactModel

interface DetailsRepository {

    fun getContact(id: String): Single<ContactModel>

    fun createContact(entity: ContactModel): Single<Unit>

    fun updateContact(entity: ContactModel): Single<Unit>
}
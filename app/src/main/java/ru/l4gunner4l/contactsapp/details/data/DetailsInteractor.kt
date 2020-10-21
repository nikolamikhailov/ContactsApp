package ru.l4gunner4l.contactsapp.details.data

import ru.l4gunner4l.contactsapp.base.model.ContactModel

class DetailsInteractor(
    private val contactsRepository: DetailsRepository
) {
    fun getContact(id: String) = contactsRepository.getContact(id)

    fun createContact(contactModel: ContactModel) = contactsRepository.createContact(contactModel)

    fun updateContact(contactModel: ContactModel) = contactsRepository.updateContact(contactModel)
}
package ru.l4gunner4l.contactsapp.contacts.data

import javax.inject.Inject


class ContactsInteractor @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    fun getContacts() = contactsRepository.getContacts()
}

package ru.l4gunner4l.contactsapp.contacts.data

class ContactsInteractor(
    private val contactsRepository: ContactsRepository
) {

    fun getContacts() = contactsRepository.getContacts()
}

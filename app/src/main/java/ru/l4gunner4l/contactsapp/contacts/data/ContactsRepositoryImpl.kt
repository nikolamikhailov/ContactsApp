package ru.l4gunner4l.contactsapp.contacts.data

import io.reactivex.Single
import ru.l4gunner4l.contactsapp.base.mapToEntityModel
import ru.l4gunner4l.contactsapp.base.mapToUiModel
import ru.l4gunner4l.contactsapp.base.model.ContactModel
import ru.l4gunner4l.contactsapp.contacts.data.local.ContactsDao
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val contactsDao: ContactsDao
) : ContactsRepository {

    override fun getContacts(): Single<List<ContactModel>> {
        return contactsDao.read().map { list ->
            list.map { it.mapToUiModel() }
        }
    }

    override fun deleteContact(entity: ContactModel): Single<Unit> {
        return contactsDao.delete(entity.mapToEntityModel())
    }
}
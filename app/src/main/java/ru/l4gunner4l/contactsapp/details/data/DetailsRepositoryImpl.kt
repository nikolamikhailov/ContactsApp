package ru.l4gunner4l.contactsapp.details.data

import io.reactivex.Single
import ru.l4gunner4l.contactsapp.base.mapToEntityModel
import ru.l4gunner4l.contactsapp.base.mapToUiModel
import ru.l4gunner4l.contactsapp.base.model.ContactModel
import ru.l4gunner4l.contactsapp.contacts.data.local.ContactsDao
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val contactsDao: ContactsDao
) : DetailsRepository {

    override fun getContact(id: String): Single<ContactModel> {
        return contactsDao.readSingle(id).map { it.mapToUiModel() }
    }

    override fun createContact(entity: ContactModel): Single<Unit> {
        return contactsDao.create(entity.mapToEntityModel())
    }

    override fun updateContact(entity: ContactModel): Single<Unit> {
        return contactsDao.update(entity.mapToEntityModel())
    }
}
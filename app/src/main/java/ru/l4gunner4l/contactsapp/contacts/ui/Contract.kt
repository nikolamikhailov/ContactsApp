package ru.l4gunner4l.contactsapp.contacts.ui

import ru.l4gunner4l.contactsapp.base.Event
import ru.l4gunner4l.contactsapp.base.model.ContactModel

data class ViewState(
    val status: STATUS,
    val contactsList: List<ContactModel>
)

sealed class UiEvent() : Event {
    data class OnItemClick(val index: Int) : UiEvent()
    object OnAddContact : UiEvent()
}

sealed class DataEvent() : Event {
    object RequestContacts : DataEvent()
    data class SuccessContactsRequest(val listContactModel: List<ContactModel>) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}
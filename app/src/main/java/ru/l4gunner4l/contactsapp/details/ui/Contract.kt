package ru.l4gunner4l.contactsapp.details.ui

import ru.l4gunner4l.contactsapp.base.Event
import ru.l4gunner4l.contactsapp.base.model.ContactModel

data class ViewState(
    val status: STATUS,
    val contact: ContactModel?
)

sealed class UiEvent() : Event {
    object OnExitClick : UiEvent()
    object OnSuccessAdded : UiEvent()
    object OnErrorAdded : UiEvent()
    object OnSuccessChanged : UiEvent()
    object OnErrorChanged : UiEvent()
}

sealed class DataEvent() : Event {
    data class CreateContact(val contactModel: ContactModel) : DataEvent()
    data class UpdateContact(val contactModel: ContactModel) : DataEvent()
    data class RequestContact(val id: String) : DataEvent()
    data class SuccessContactRequest(val contactModel: ContactModel) : DataEvent()
}

enum class STATUS {
    LOAD,
    CONTENT,
    ERROR
}
package ru.l4gunner4l.contactsapp.contacts.ui

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.l4gunner4l.contactsapp.DetailsScreen
import ru.l4gunner4l.contactsapp.base.BaseViewModel
import ru.l4gunner4l.contactsapp.base.Event
import ru.l4gunner4l.contactsapp.contacts.data.ContactsInteractor
import ru.terrakok.cicerone.Router

class ContactsViewModel(
    private val interactor: ContactsInteractor,
    private val router: Router
) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState {
        return ViewState(STATUS.LOAD, emptyList())
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnItemClick -> {
                router.navigateTo(DetailsScreen(previousState.contactsList[event.index].id))
            }
            is UiEvent.OnAddContact -> {
                router.navigateTo(DetailsScreen())
            }
            is DataEvent.RequestContacts -> {
                interactor.getContacts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.SuccessContactsRequest(it))
                        },
                        {
                            it
                        }
                    )

            }
            is DataEvent.SuccessContactsRequest -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
                    contactsList = event.listContactModel
                )
            }
        }
        return null
    }


}
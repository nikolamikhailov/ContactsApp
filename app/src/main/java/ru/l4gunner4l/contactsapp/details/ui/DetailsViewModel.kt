package ru.l4gunner4l.contactsapp.details.ui

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.l4gunner4l.contactsapp.base.BaseViewModel
import ru.l4gunner4l.contactsapp.base.Event
import ru.l4gunner4l.contactsapp.details.data.DetailsInteractor
import ru.terrakok.cicerone.Router

class DetailsViewModel(
    private val id: String?,
    private val interactor: DetailsInteractor,
    private val router: Router
) : BaseViewModel<ViewState>() {

    init {
        if (id != null) processDataEvent(DataEvent.RequestContact(id))
    }

    override fun initialViewState(): ViewState {
        return ViewState(STATUS.LOAD, null)
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnExitClick -> {
                router.exit()
            }
            // здесь чтобы тост выводить надо previousState возвращать? а также создать новый STATUS?
            is UiEvent.OnSuccessAdded -> {
                Log.i("M_MAIN", "successed added")
            }
            is UiEvent.OnErrorAdded -> {
                Log.i("M_MAIN", "error added")
            }
            is UiEvent.OnSuccessChanged -> {
                Log.i("M_MAIN", "successed changed")
            }
            is UiEvent.OnErrorChanged -> {
                Log.i("M_MAIN", "error changed")
            }
            is DataEvent.CreateContact -> {
                interactor.createContact(event.contactModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { processUiEvent(UiEvent.OnSuccessAdded) },
                        { processUiEvent(UiEvent.OnErrorAdded) }
                    )
            }
            is DataEvent.UpdateContact -> {
                interactor.updateContact(event.contactModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { processUiEvent(UiEvent.OnSuccessChanged) },
                        { processUiEvent(UiEvent.OnErrorChanged) }
                    )
            }
            is DataEvent.RequestContact -> {
                interactor.getContact(id!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            processDataEvent(DataEvent.SuccessContactRequest(it))
                        },
                        {
                            it
                        }
                    )
            }
            is DataEvent.SuccessContactRequest -> {
                return previousState.copy(
                    status = STATUS.CONTENT,
                    contact = event.contactModel
                )
            }
        }
        return null
    }


}
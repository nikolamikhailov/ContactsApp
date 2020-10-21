package ru.l4gunner4l.contactsapp.base

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.l4gunner4l.contactsapp.contacts.data.ContactsInteractor
import ru.l4gunner4l.contactsapp.contacts.data.ContactsRepository
import ru.l4gunner4l.contactsapp.contacts.data.ContactsRepositoryImpl
import ru.l4gunner4l.contactsapp.contacts.data.local.ContactsDatabase
import ru.l4gunner4l.contactsapp.contacts.ui.ContactsViewModel
import ru.l4gunner4l.contactsapp.details.data.DetailsInteractor
import ru.l4gunner4l.contactsapp.details.data.DetailsRepository
import ru.l4gunner4l.contactsapp.details.data.DetailsRepositoryImpl
import ru.l4gunner4l.contactsapp.details.ui.DetailsViewModel
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


const val CONTACTS_TABLE = "CONTACTS_TABLE"
const val CONTACTS_QUALIFIER = "CONTACTS_QUALIFIER"

val navModule = module {

    single<Cicerone<Router>>(named(CONTACTS_QUALIFIER)) {
        Cicerone.create()
    }

    single<NavigatorHolder>(named(CONTACTS_QUALIFIER)) {
        get<Cicerone<Router>>(named(CONTACTS_QUALIFIER)).navigatorHolder
    }

    single<Router>(named(CONTACTS_QUALIFIER)) {
        get<Cicerone<Router>>(named(CONTACTS_QUALIFIER)).router
    }

}

val contactsModule = module {

    single {
        Room
            .databaseBuilder(androidContext(), ContactsDatabase::class.java, CONTACTS_TABLE)
            .build()
    }
    single {
        get<ContactsDatabase>().contactsDao()
    }

    single<ContactsRepository> {
        ContactsRepositoryImpl(get())
    }

    single<ContactsInteractor> {
        ContactsInteractor(get())
    }

    viewModel<ContactsViewModel> {
        ContactsViewModel(get(), get(named(CONTACTS_QUALIFIER)))
    }


    single<DetailsRepository> {
        DetailsRepositoryImpl(get())
    }

    single<DetailsInteractor> {
        DetailsInteractor(get())
    }

    viewModel<DetailsViewModel> { (id: String?) ->
        DetailsViewModel(
            id,
            get(),
            get(named(CONTACTS_QUALIFIER))
        )
    }
}

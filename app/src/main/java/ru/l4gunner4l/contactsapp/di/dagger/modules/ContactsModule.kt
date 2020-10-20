package ru.l4gunner4l.contactsapp.di.dagger.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.l4gunner4l.contactsapp.contacts.data.ContactsInteractor
import ru.l4gunner4l.contactsapp.contacts.data.ContactsRepository
import ru.l4gunner4l.contactsapp.contacts.data.ContactsRepositoryImpl
import ru.l4gunner4l.contactsapp.contacts.data.local.ContactsDao
import ru.l4gunner4l.contactsapp.contacts.ui.ContactsFragment
import ru.l4gunner4l.contactsapp.contacts.ui.ContactsViewModel
import ru.l4gunner4l.contactsapp.di.dagger.ViewModelBuilder
import ru.l4gunner4l.contactsapp.di.dagger.ViewModelKey
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
abstract class ContactsModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesContactsRepository(dao: ContactsDao): ContactsRepository {
            return ContactsRepositoryImpl(dao)
        }


        @Provides
        @JvmStatic
        fun providesContactsInteractor(repo: ContactsRepository): ContactsInteractor {
            return ContactsInteractor(repo)
        }

        @Provides
        @JvmStatic
        fun providesContactsViewModel(
            interactor: ContactsInteractor,
            @Named(NavModule.ROUTER_QUALIFIER) router: Router
        ): ContactsViewModel {
            return ContactsViewModel(interactor, router)
        }
    }


    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun provideContactsFragment(): ContactsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    abstract fun bindViewModel(viewModel: ContactsViewModel): ViewModel

}
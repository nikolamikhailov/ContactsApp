package ru.l4gunner4l.contactsapp.di.dagger.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.l4gunner4l.contactsapp.contacts.data.local.ContactsDao
import ru.l4gunner4l.contactsapp.details.data.DetailsInteractor
import ru.l4gunner4l.contactsapp.details.data.DetailsRepository
import ru.l4gunner4l.contactsapp.details.data.DetailsRepositoryImpl
import ru.l4gunner4l.contactsapp.details.ui.DetailsFragment
import ru.l4gunner4l.contactsapp.details.ui.DetailsViewModel
import ru.l4gunner4l.contactsapp.di.dagger.ViewModelBuilder
import ru.l4gunner4l.contactsapp.di.dagger.ViewModelKey
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
abstract class DetailsModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDetailsRepository(dao: ContactsDao): DetailsRepository {
            return DetailsRepositoryImpl(dao)
        }

        @Provides
        @JvmStatic
        fun providesDetailsInteractor(repo: DetailsRepository): DetailsInteractor {
            return DetailsInteractor(repo)
        }

        @Provides
        @JvmStatic
        fun providesDetailsViewModel(
            interactor: DetailsInteractor,
            @Named(NavModule.ROUTER_QUALIFIER) router: Router
        ): DetailsViewModel {
            return DetailsViewModel(null, interactor, router)
        }
    }


    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun provideDetailsFragment(): DetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindViewModel(viewModel: DetailsViewModel): ViewModel

}
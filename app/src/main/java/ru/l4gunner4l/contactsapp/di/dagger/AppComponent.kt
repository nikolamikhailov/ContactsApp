package ru.l4gunner4l.contactsapp.di.dagger

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.l4gunner4l.contactsapp.App
import ru.l4gunner4l.contactsapp.di.dagger.modules.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainScreenModule::class,
        AndroidSupportInjectionModule::class,
        ContactsModule::class,
        RoomModule::class,
        NavModule::class,
        DetailsFragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
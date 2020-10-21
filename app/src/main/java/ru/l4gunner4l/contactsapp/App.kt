package ru.l4gunner4l.contactsapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.l4gunner4l.contactsapp.base.contactsModule
import ru.l4gunner4l.contactsapp.base.navModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(navModule, contactsModule)
        }
    }
}
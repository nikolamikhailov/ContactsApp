package ru.l4gunner4l.contactsapp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ru.l4gunner4l.contactsapp.di.dagger.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}
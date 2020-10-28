package ru.l4gunner4l.contactsapp.di.dagger.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.l4gunner4l.contactsapp.HolderFragment

@Module
abstract class MainScreenModule {

    @ContributesAndroidInjector()
    internal abstract fun provideHolderFragment(): HolderFragment

}
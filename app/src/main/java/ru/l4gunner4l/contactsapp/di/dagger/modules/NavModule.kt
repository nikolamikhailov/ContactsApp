package ru.l4gunner4l.contactsapp.di.dagger.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Named
import javax.inject.Singleton

@Module
class NavModule {


    @Module
    companion object {
        const val NAV_HOLDER_QUALIFIER = "NAV_HOLDER"
        const val ROUTER_QUALIFIER = "ROUTER"
        const val CICERONE_QUALIFIER = "CICERONE"

        @Provides
        @Singleton
        @JvmStatic
        @Named(CICERONE_QUALIFIER)
        fun providesCicerone(): Cicerone<Router> {
            return Cicerone.create()
        }

        @Provides
        @Singleton
        @JvmStatic
        @Named(NAV_HOLDER_QUALIFIER)
        fun providesNavigatorHolder(
            @Named(CICERONE_QUALIFIER) cicerone: Cicerone<Router>
        ): NavigatorHolder {
            return cicerone.navigatorHolder
        }

        @Provides
        @Singleton
        @JvmStatic
        @Named(ROUTER_QUALIFIER)
        fun providesRouter(
            @Named(CICERONE_QUALIFIER) cicerone: Cicerone<Router>
        ): Router {
            return cicerone.router
        }
    }
}
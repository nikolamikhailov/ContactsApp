package ru.l4gunner4l.contactsapp

import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerFragment
import ru.l4gunner4l.contactsapp.di.dagger.modules.NavModule.Companion.NAV_HOLDER_QUALIFIER
import ru.l4gunner4l.contactsapp.di.dagger.modules.NavModule.Companion.ROUTER_QUALIFIER
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class HolderFragment : DaggerFragment(R.layout.fragment_holder) {

    companion object {
        fun newInstance() = HolderFragment()
    }

    private val navigator: Navigator by lazy { createNavigator() }

    @Inject
    @Named(ROUTER_QUALIFIER)
    lateinit var router: Router

    @Inject
    @Named(NAV_HOLDER_QUALIFIER)
    lateinit var navigatorHolder: NavigatorHolder


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router.navigateTo(ContactsScreen())
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    private fun createNavigator() =
        SupportAppNavigator(
            requireActivity(),
            childFragmentManager,
            R.id.fragmentHolder
        )
}
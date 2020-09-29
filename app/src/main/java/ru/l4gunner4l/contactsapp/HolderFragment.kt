package ru.l4gunner4l.contactsapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.l4gunner4l.contactsapp.base.CONTACTS_QUALIFIER
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class HolderFragment : Fragment(R.layout.fragment_holder) {

    companion object {
        fun newInstance() = HolderFragment()
    }

    private val navigator: Navigator by lazy { createNavigator() }
    private val router: Router by inject(named(CONTACTS_QUALIFIER))
    private val navigatorHolder: NavigatorHolder by inject(named(CONTACTS_QUALIFIER))


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
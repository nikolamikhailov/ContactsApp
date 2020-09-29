package ru.l4gunner4l.contactsapp

import androidx.fragment.app.Fragment
import ru.l4gunner4l.contactsapp.contacts.ui.ContactsFragment
import ru.l4gunner4l.contactsapp.details.ui.DetailsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ContactsScreen : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return ContactsFragment.newInstance()
    }
}


class DetailsScreen(private val id: String? = null) : SupportAppScreen() {
    override fun getFragment(): Fragment? {
        return DetailsFragment.newInstance(id)
    }
}

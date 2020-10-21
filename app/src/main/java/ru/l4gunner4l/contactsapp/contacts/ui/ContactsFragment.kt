package ru.l4gunner4l.contactsapp.contacts.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.l4gunner4l.contactsapp.DetailsScreen
import ru.l4gunner4l.contactsapp.R
import ru.l4gunner4l.contactsapp.base.CONTACTS_QUALIFIER
import ru.l4gunner4l.contactsapp.base.model.ContactModel
import ru.terrakok.cicerone.Router


class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    // QQQ как понять когда юзать lateinit а когда lazy?
    // QQQ как понять что делить на дата ивент а что на ui ивент?

    companion object {
        fun newInstance() = ContactsFragment()
    }

    private val router: Router by inject(named(CONTACTS_QUALIFIER))
    private val viewModel: ContactsViewModel by viewModel()
    private lateinit var adapter: ContactsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
        viewModel.processDataEvent(DataEvent.RequestContacts)
        initUi()
    }

    private fun initUi() {
        adapter = ContactsAdapter { position ->
            viewModel.processUiEvent(UiEvent.OnItemClick(position))
        }
        rvContacts.adapter = adapter
        rvContacts.layoutManager = LinearLayoutManager(requireContext())
        rvContacts.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        btnAddContact.setOnClickListener {
            router.navigateTo(DetailsScreen())
        }
    }

    private fun render(viewState: ViewState) {
        when (viewState.status) {
            STATUS.CONTENT -> {
                updateUi(viewState.contactsList)
            }
            STATUS.LOAD -> {

            }
            STATUS.ERROR -> {

            }
        }
    }

    private fun updateUi(contactsList: List<ContactModel>) {
        adapter.setData(contactsList)
        rvContacts.isVisible = contactsList.isNotEmpty()
        tvEmptyContacts.isVisible = contactsList.isEmpty()
    }
}
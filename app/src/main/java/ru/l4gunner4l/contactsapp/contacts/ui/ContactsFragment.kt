package ru.l4gunner4l.contactsapp.contacts.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_contacts.*
import ru.l4gunner4l.contactsapp.DetailsScreen
import ru.l4gunner4l.contactsapp.R
import ru.l4gunner4l.contactsapp.base.model.ContactModel
import ru.l4gunner4l.contactsapp.di.dagger.modules.NavModule
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named


class ContactsFragment : DaggerFragment(R.layout.fragment_contacts) {

    companion object {
        fun newInstance() = ContactsFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    @Named(NavModule.ROUTER_QUALIFIER)
    lateinit var router: Router
    private val viewModel by viewModels<ContactsViewModel> { factory }

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
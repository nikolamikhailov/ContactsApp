package ru.l4gunner4l.contactsapp.contacts.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*
import ru.l4gunner4l.contactsapp.R
import ru.l4gunner4l.contactsapp.base.model.ContactModel

class ContactsAdapter(
    private var contacts: List<ContactModel> = emptyList(),
    private val onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    fun setData(contactsList: List<ContactModel>) {
        contacts = contactsList
        notifyDataSetChanged()
    }

    inner class VH(override val containerView: View) : LayoutContainer,
        RecyclerView.ViewHolder(containerView) {

        init {
            containerView.setOnClickListener {
                onClick(adapterPosition)
            }
        }

        fun bind(contactModel: ContactModel) =
            with(contactModel) {
                Glide.with(containerView)
                    .load(
                        if (imagePath != "") imagePath
                        else containerView.resources.getDrawable(
                            R.drawable.ic_baseline_person_24, null
                        )
                    )
                    .error(R.drawable.ic_baseline_person_24)
                    .into(itemImage)
                itemTvName.text = name
                itemTvPhone.text = phone
            }
    }
}
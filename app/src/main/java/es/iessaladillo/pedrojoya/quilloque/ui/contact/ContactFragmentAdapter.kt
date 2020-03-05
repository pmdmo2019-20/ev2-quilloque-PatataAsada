package es.iessaladillo.pedrojoya.quilloque.ui.contact


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.model.Contact
import es.iessaladillo.pedrojoya.quilloque.utils.createAvatarDrawable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.contacts_fragment_item.*

class ContactFragmentAdapter :
    ListAdapter<Contact, ContactFragmentAdapter.ViewHolder>(ContactDiffCallBack) {

    private var onItemClickListener: ((Int) -> Unit)? = null
    var onDeleteListener: ((Int) -> Unit)? = null
    var onCallListener: ((Int) -> Unit)? = null
    var onMessageListener: ((Int) -> Unit)? = null
    var onVideoListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.contacts_fragment_item, parent, false)
        return ViewHolder(itemView, onItemClickListener,onCallListener,onMessageListener,onVideoListener,onDeleteListener)
    }

    fun setOnClickListener(listener: ((Int) -> Unit)?) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    object ContactDiffCallBack : DiffUtil.ItemCallback<Contact>() {

        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.name == newItem.name && oldItem.phoneNumber == newItem.phoneNumber

    }

    class ViewHolder(
        override val containerView: View,
        onItemClickListener: ((Int) -> Unit)?,
        onCallListener: ((Int) -> Unit)?,
        onMessageListener: ((Int) -> Unit)?,
        onVideoListener: ((Int) -> Unit)?,
        onDeleteListener: ((Int) -> Unit)?
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }
            btnCall.setOnClickListener { onCallListener?.invoke(adapterPosition) }
            btnDelete.setOnClickListener { onDeleteListener?.invoke(adapterPosition) }
            btnMessage.setOnClickListener { onMessageListener?.invoke(adapterPosition) }
            btnVideoCall.setOnClickListener { onVideoListener?.invoke(adapterPosition) }
        }

        fun bind(contact: Contact) {
            lblName.text = contact.name
            lblPhoneNumber.text = contact.phoneNumber
            imgAvatar.setImageDrawable(createAvatarDrawable(contact.name))
        }

    }
}

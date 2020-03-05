package es.iessaladillo.pedrojoya.quilloque.ui.recent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.getCallTypeIcon
import es.iessaladillo.pedrojoya.quilloque.data.model.CallWithContact
import es.iessaladillo.pedrojoya.quilloque.utils.createAvatarDrawable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.contacts_fragment_item.imgAvatar
import kotlinx.android.synthetic.main.contacts_fragment_item.lblName
import kotlinx.android.synthetic.main.recent_fragment_item.*

class RecentFragmentAdapter: ListAdapter<CallWithContact, RecentFragmentAdapter.ViewHolder>(RecentDiffCallBack) {

    var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recent_fragment_item, parent, false)
        return ViewHolder(itemView, onItemClickListener)
    }

    fun setOnClickListener(listener: ((Int) -> Unit)?) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    object RecentDiffCallBack : DiffUtil.ItemCallback<CallWithContact>() {

        override fun areItemsTheSame(oldItem: CallWithContact, newItem: CallWithContact): Boolean =
            oldItem.callid == newItem.callid || oldItem.contactId == newItem.contactId

        override fun areContentsTheSame(oldItem: CallWithContact, newItem: CallWithContact): Boolean =
            oldItem.contactName == newItem.contactName && oldItem.phoneNumber == newItem.phoneNumber

    }

    class ViewHolder(
        override val containerView: View,
        onItemClickListener: ((Int) -> Unit)?
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }
        }

        fun bind(callWithContact: CallWithContact) {
            lblName.text = callWithContact.contactName
            lblTime.text = callWithContact.time
            lblDate.text = callWithContact.date
            imgCallType.setImageResource(getCallTypeIcon(callWithContact.type!!))
            imgAvatar.setImageDrawable(createAvatarDrawable(callWithContact.contactName!!))
        }

    }
}


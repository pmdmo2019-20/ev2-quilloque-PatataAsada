package es.iessaladillo.pedrojoya.quilloque.ui.contact

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.MainViewModel
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.CALL_TYPE_MADE
import es.iessaladillo.pedrojoya.quilloque.data.CALL_TYPE_MISSED
import es.iessaladillo.pedrojoya.quilloque.data.CALL_TYPE_RECEIVED
import es.iessaladillo.pedrojoya.quilloque.data.CALL_TYPE_VIDEO
import es.iessaladillo.pedrojoya.quilloque.data.model.Call
import es.iessaladillo.pedrojoya.quilloque.data.model.Contact
import es.iessaladillo.pedrojoya.quilloque.utils.makeToast
import kotlinx.android.synthetic.main.contacts_fragment.*
import kotlinx.android.synthetic.main.dial_fragment.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class ContactsFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    private val contactFragmentAdapter = ContactFragmentAdapter().apply {
        onCallListener = { createCall(currentList[it], CALL_TYPE_MADE) }

        onMessageListener = { createCall(currentList[it], CALL_TYPE_MISSED) }

        onVideoListener = { createCall(currentList[it], CALL_TYPE_VIDEO) }

        onDeleteListener = { createCall(currentList[it], CALL_TYPE_RECEIVED) }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createCall(contact: Contact, type: String) {
        if (type != CALL_TYPE_RECEIVED) {
            val date: String = SimpleDateFormat("yyyy/MM/dd").format(
                Date()
            )
            val time: String = SimpleDateFormat("HH:mm").format(
                Date()
            )
            viewModel.createCall(Call(0, contact.phoneNumber, type, time, date))
            val message = when (type) {
                CALL_TYPE_VIDEO -> String.format("se ha enviado video a %s", contact.name)
                CALL_TYPE_MISSED -> String.format("se ha enviado mensaje a %s", contact.name)
                else -> String.format("se ha llamado a %s", contact.name)
            }
            makeToast(message, requireContext())
        } else {
            viewModel.deleteContact(contact)
            makeToast(String.format("se ha borrado a %s", contact.name), requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupReciclerView()
        setupViewmodel()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.contacts.observe(viewLifecycleOwner) { updateContacts() }
    }

    private fun updateContacts() {
        contactFragmentAdapter.submitList(viewModel.contacts.value)
        emptyView.isVisible = viewModel.contacts.value!!.isEmpty()
    }

    private fun setupViewmodel() {
        viewModel = requireActivity().run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

    private fun setupReciclerView() {
        lstContacts.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 1)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    RecyclerView.VERTICAL
                )
            )
            adapter = contactFragmentAdapter
        }
    }
}
package es.iessaladillo.pedrojoya.quilloque.ui.contact

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ContactsFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    private val contactFragmentAdapter = ContactFragmentAdapter().apply{
        onCallListener = { createCall(currentList[it], CALL_TYPE_MADE) }

        onMessageListener = { createCall(currentList[it], CALL_TYPE_MISSED) }

        onVideoListener = { createCall(currentList[it], CALL_TYPE_VIDEO) }

        onDeleteListener = { createCall(currentList[it], CALL_TYPE_RECEIVED) }
    }

    @SuppressLint("NewApi")
    private fun createCall(contact: Contact, type: String) {
        if(type!= CALL_TYPE_RECEIVED){
            val date:String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            val time:String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            viewModel.createCall(Call(0,contact.phoneNumber, type,time,date))
            makeToast(String().format("se ha llamado a %s", contact.name),context!!)
        }
        else{
            val date:String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            val time:String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            viewModel.deleteContact(contact)
            makeToast(String().format("se ha llamado a %s", contact.name),context!!)
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
    }

    private fun setupViewmodel() {
        viewModel = requireActivity().run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

    private fun setupReciclerView() {
        lstContacts.run {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(requireContext(),
                    RecyclerView.VERTICAL)
            )
            adapter = contactFragmentAdapter
        }
    }
}
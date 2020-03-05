package es.iessaladillo.pedrojoya.quilloque

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.quilloque.data.Consultas
import es.iessaladillo.pedrojoya.quilloque.data.model.Call
import es.iessaladillo.pedrojoya.quilloque.data.model.CallWithContact
import es.iessaladillo.pedrojoya.quilloque.data.model.Contact

class MainViewModel(private val application: Application, private val db: Consultas) : ViewModel() {

    private var _searchContact: MutableLiveData<String> = MutableLiveData("")
    val searchContact: LiveData<String> get() = _searchContact

    private val search = "%" + searchContact.value!! + "%"

    private var _contacts: MutableLiveData<List<Contact>> =
        MutableLiveData(db.consultasDao.queryContacts(search))
    val contacts: LiveData<List<Contact>> get() = _contacts

    private var _callsWithContacts: MutableLiveData<List<CallWithContact>> =
        MutableLiveData(db.consultasDao.queryCalls(10))
    val callsWithContacts: LiveData<List<CallWithContact>> get() = _callsWithContacts

    var contactCreatorHelper: Call? = null

    fun createCall(call: Call) {
        db.consultasDao.insertCall(call)
        updateCalls()
    }

    fun deleteCall(call: Call) {
        db.consultasDao.deleteCall(call)
    }

    private fun updateCalls() {
        _callsWithContacts.value = db.consultasDao.queryCalls(10)
    }

    fun createContact(contact: Contact) {
        db.consultasDao.insertContact(contact)
        updateContacts()
    }

    fun deleteContact(contact: Contact) {
        db.consultasDao.deleteContact(contact)
        updateContacts()
    }

    private fun updateContacts() {
        _contacts.value = db.consultasDao.queryContacts(search)
    }
}
package es.iessaladillo.pedrojoya.quilloque.ui.contactCreation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.quilloque.MainViewModel
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.model.Contact
import es.iessaladillo.pedrojoya.quilloque.utils.hideSoftKeyboard
import kotlinx.android.synthetic.main.contact_creation_fragment.*

class ContactCreationFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_creation_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupNumberIfCreatingFromRecent()
        setupButtons()
    }

    private fun setupButtons() {
        fabSave.setOnClickListener { saveContact() }
    }

    private fun saveContact() {
        if(txtName.text.isNotEmpty()&&txtPhoneNumber.text.isNotEmpty()) {
            viewModel.createContact(Contact(0,txtName.text.toString(),txtPhoneNumber.text.toString()))
            requireView().hideSoftKeyboard()
            requireActivity().onBackPressed()
        }
    }

    private fun setupNumberIfCreatingFromRecent() {
        if(viewModel.contactCreatorHelper != null){
            txtPhoneNumber.setText(viewModel.contactCreatorHelper)
        }
    }

    private fun setupViewModel() {
        viewModel = requireActivity().run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }


}

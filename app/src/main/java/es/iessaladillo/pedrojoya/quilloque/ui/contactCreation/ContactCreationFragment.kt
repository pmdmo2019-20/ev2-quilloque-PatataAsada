package es.iessaladillo.pedrojoya.quilloque.ui.contactCreation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import es.iessaladillo.pedrojoya.quilloque.R

class ContactCreationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_creation_fragment,container,false)
    }

}

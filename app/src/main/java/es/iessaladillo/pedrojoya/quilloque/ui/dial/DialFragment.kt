package es.iessaladillo.pedrojoya.quilloque.ui.dial

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.MainViewModel
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.CALL_TYPE_MADE
import es.iessaladillo.pedrojoya.quilloque.data.CALL_TYPE_VIDEO
import es.iessaladillo.pedrojoya.quilloque.data.model.Call
import es.iessaladillo.pedrojoya.quilloque.data.model.CallWithContact
import es.iessaladillo.pedrojoya.quilloque.utils.makeToast
import kotlinx.android.synthetic.main.dial_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import java.text.SimpleDateFormat
import java.util.*

class DialFragment : Fragment() {
    private val navCtrl: NavController by lazy {
        findNavController()
    }

    private val dialFragmentAdapter = DialFragmentAdapter().also {
        it.setOnClickListener { position -> typeNumber(it.currentList[position].phoneNumber) }
    }

    private fun typeNumber(phoneNumber: String) {
        lblNumber.text = phoneNumber
    }

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dial_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewmodel()
        setupObservers()
        setupRecyclerView()
        setupButtons()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setupButtons() {
        lblCreateContact.setOnClickListener { navigateToCreateContact() }
        lblOne.setOnClickListener { addText("1") }
        lblTwo.setOnClickListener { addText("2") }
        lblThree.setOnClickListener { addText("3") }
        lblFour.setOnClickListener { addText("4") }
        lblFive.setOnClickListener { addText("5") }
        lblSix.setOnClickListener { addText("6") }
        lblSeven.setOnClickListener { addText("7") }
        lblEight.setOnClickListener { addText("8") }
        lblNine.setOnClickListener { addText("9") }
        lblAstherisc.setOnClickListener { addText("*") }
        lblPound.setOnClickListener { addText("#") }

        imgBackspace.setOnClickListener { deleteOne() }

        imgVideo.setOnClickListener {
            if (lblNumber.text.isNotEmpty()) {
                viewModel.createCall(
                    Call(
                        0, lblNumber.text.toString(),
                        CALL_TYPE_VIDEO, SimpleDateFormat("HH:mm").format(
                            Date()
                        ), SimpleDateFormat("yyyy/MM/dd").format(
                            Date()
                        )
                    )
                )
                makeToast(String.format("Videollamada a %s",lblNumber.text),requireContext())
            }
        }

        fabCall.setOnClickListener {
            if (lblNumber.text.isNotEmpty()) {
                viewModel.createCall(
                    Call(
                        0, lblNumber.text.toString(),
                        CALL_TYPE_MADE, SimpleDateFormat("HH:mm").format(
                            Date()
                        ), SimpleDateFormat("yyyy/MM/dd").format(
                            Date()
                        )
                    )
                )
                makeToast(String.format("Se ha llamado a %s",lblNumber.text),requireContext())
            }
        }

    }

    private fun deleteOne() {
        if (lblNumber.text.isNotEmpty()) lblNumber.text =
            lblNumber.text.subSequence(0, lblNumber.text.length - 1)
    }

    @SuppressLint("SetTextI18n")
    private fun addText(text: String) {
        lblNumber.text = lblNumber.text.toString() + text
    }

    private fun navigateToCreateContact() {
        viewModel.arrayBackstackTitles.add(getString(R.string.dial_title))
        viewModel.contactCreatorHelper = lblNumber.text.toString()
        viewModel.changeToolbarTitle(getString(R.string.contact_creation_title))
        navCtrl.navigate(R.id.action_dialFragment_to_contactCreationFragment)
    }

    private fun setupRecyclerView() {
        lstSuggestions.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 1)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    RecyclerView.VERTICAL
                )
            )
            adapter = dialFragmentAdapter
        }
    }

    private fun setupObservers() {
        viewModel.callsSugested.observe(viewLifecycleOwner) {
            updateSugested()
        }
    }

    private fun updateSugested() {
        dialFragmentAdapter.submitList(viewModel.callsSugested.value!!)
    }

    private fun setupViewmodel() {
        viewModel = requireActivity().run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }
}


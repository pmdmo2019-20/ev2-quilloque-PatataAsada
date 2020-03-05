package es.iessaladillo.pedrojoya.quilloque.ui.recent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.MainViewModel

import es.iessaladillo.pedrojoya.quilloque.R
import kotlinx.android.synthetic.main.recent_fragment.*


class RecentFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    val recentFragmentAdapter = RecentFragmentAdapter().also {
        it.onItemClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recent_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewmodel()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.callsWithContacts.observe(viewLifecycleOwner){
            updateCalls()
        }
    }

    private fun updateCalls() {
        recentFragmentAdapter.submitList(viewModel.callsWithContacts.value)
        emptyView.isVisible = viewModel.callsWithContacts.value!!.isEmpty()
    }

    private fun setupRecyclerView() {
        lstCalls.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),1)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(requireContext(),
                    RecyclerView.VERTICAL)
            )
            adapter = recentFragmentAdapter
        }
    }

    private fun setupViewmodel() {
        viewModel = requireActivity().run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

}

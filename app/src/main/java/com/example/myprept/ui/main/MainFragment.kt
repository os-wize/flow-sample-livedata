package com.example.myprept.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myprept.R
import com.example.myprept.utils.UiStateManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


@AndroidEntryPoint
class MainFragment : Fragment(), UiStateManager, SearchView.OnQueryTextListener {

    lateinit var searchView: SearchView

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: ReposViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        searchView.setOnQueryTextListener(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initStateObserver(this, viewModel.uiState)
    }

    override val onSuccessEmpty  = { println("err")}


    override val onError = { println("err")}
    override val onErrorEmpty = { println("err")}
    override val onErrorConnection = { println("err")}
    override val onLoading = { println("err")}
    override val onLoaded = { println("err")}

    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
     if(!newText.isNullOrEmpty()) {
         viewModel.searchRepo(newText)
     }
        return true
    }

}
package com.wsa.shows.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wsa.shows.ui.component.GridViewComponent.ShowGridView
import com.wsa.shows.ui.model.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold {
                    showSearchBar()
                    showGridView(LocalContext.current)
                }

            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun showSearchBar() {
        var text by remember { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }

        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = text,
            onQueryChange = {
                text = it
                println("====> $text")
            },
            onSearch = { query ->
                active = false
                homeViewModel.onSearch(query)
                println("====> OnSearch Clicked")
            },
            onActiveChange = {

                active = it
                println("====> $active")
            },
            active = false,

            ) {


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
    }

    private fun addObservers() {
        //   homeViewModel.isLoading
    }

    @Composable
    fun showGridView(context: Context) {
        val gridList = homeViewModel.getGridFlow().collectAsState()
        ShowGridView(gridList, context)
    }

}

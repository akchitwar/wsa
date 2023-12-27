package com.wsa.shows.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.wsa.shows.obj.ItemModel
import com.wsa.shows.ui.component.FavoritePagerComponent
import com.wsa.shows.ui.component.GridViewComponent.ShowGridView
import com.wsa.shows.ui.component.LoadingDialog
import com.wsa.shows.ui.model.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .background(Color.White)
                            .padding(4.dp, 4.dp),
                        verticalArrangement = Arrangement.SpaceBetween

                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .weight(0.8f, true),

                            ) {
                            ShowSearchBar()
                            ShowGridView()

                        }

                        favoriteView(Modifier.fillMaxWidth().fillMaxHeight().weight(0.2f, false)) {
                            launchDetailsFragment(it)
                        }

                        LoadingDialog(isLoading = homeViewModel.isLoading.collectAsState().value)
                    }

                }

            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowSearchBar() {
        var text by remember { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }

        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = text,
            onQueryChange = {
                text = it
                if (text.isEmpty()) homeViewModel.onSearch(text)
            },

            onSearch = { query ->
                active = false
                homeViewModel.onSearch(query)

            },
            onActiveChange = {

                active = it
                println("====> $active")
            },
            placeholder = {
               Text(text = "Search")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "search_icon")
                 },
            active = false,
            // shape = RectangleShape,
            colors = SearchBarDefaults.colors(dividerColor = Color.Black)
        ) {


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
    }

    private fun addObservers() {

        homeViewModel.toastMessageLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getError().collect { msg ->
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    @Composable
    fun ShowGridView() {
        val gridList = homeViewModel.getTrShowsFlow().collectAsState()
        ShowGridView(Modifier.padding(5.dp).fillMaxHeight(), gridList.value) {
            launchDetailsFragment(it)
        }
    }

    private fun launchDetailsFragment(it: ItemModel) {
        val fragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("selected_item", it)
        fragment.arguments = bundle
        FragmentTransaction.addChildFragment(requireActivity(), fragment)
    }

    @Composable
    fun favoriteView(modifier: Modifier = Modifier, onClick: (item: ItemModel) -> Unit) {
        val favorites = homeViewModel.getFavoritesFlow().collectAsState(listOf()).value
        FavoritePagerComponent.HorizontalPager(
            favorites,
            modifier = modifier, onClick

        )

    }

}

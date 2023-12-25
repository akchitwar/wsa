package com.wsa.shows.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsa.shows.network.NetworkHelper
import com.wsa.shows.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getGridFlow() = homeRepository.gridItemList

    init {
        fetchShows()
    }

    fun fetchShows() {
        if (!networkHelper.isNetworkAvailable()) return
        viewModelScope.launch {
            _isLoading.emit(true)
            homeRepository.refreshTrendingShows()
                .catch { }
                .onCompletion {
                    _isLoading.emit(false)
                }
                .collect()
        }
    }

    fun searchShows(query: String) {
        if (!networkHelper.isNetworkAvailable()) return
        viewModelScope.launch {
            _isLoading.emit(true)
            homeRepository.searchShows(query)
                .catch { }
                .onCompletion {
                    _isLoading.emit(false)
                }
                .collect()
        }
    }


    fun onSearch(text: String) {
        println("===> isNetworkAvailable ${networkHelper.isNetworkAvailable()}")

        //avoiding to call api again
        if (isLoading.value) return
        if (text.isEmpty()) {
            fetchShows()
            //fetch Trending show  data from api or db
        } else {
            searchShows(text)
        }

    }


}
package com.wsa.shows.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsa.shows.constants.Messages.NO_DATA_FOUND
import com.wsa.shows.constants.Messages.NO_NETWORK
import com.wsa.shows.network.NetworkHelper
import com.wsa.shows.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _toastMessageLiveData= MutableLiveData("")
    val toastMessageLiveData : LiveData<String> = _toastMessageLiveData
    fun isNetworkAvailable() = networkHelper.isNetworkAvailable()
    fun getTrShowsFlow() = homeRepository.gridItemList

    fun getError() = homeRepository.error

    fun getFavoritesFlow() = homeRepository.getFavoritesFromDb()



    init {
        checkTrShowsInDb()
        fetchShowsDb()
        if(networkHelper.isNetworkAvailable()){
            fetchShows()
        }

    }

    private fun checkTrShowsInDb() {
        viewModelScope.launch {
            if (!networkHelper.isNetworkAvailable()) {
               val status = homeRepository.isTrShowsAvailableInDb().toList().isEmpty()
                if(status){
                    sendMessage(NO_DATA_FOUND)
                }

            }
        }
    }

    private fun fetchShows() {
        viewModelScope.launch {
            homeRepository.refreshTrendingShows()
                .onCompletion {
                    _isLoading.emit(false)
                }
                .collect()
        }
    }

    fun fetchShowsDb(){
        viewModelScope.launch {
            homeRepository.getTrShowFromDb()
        }

    }

    private fun sendMessage(message: String){
        _toastMessageLiveData.postValue(message)
    }
    private fun searchShows(query: String) {
        if (!networkHelper.isNetworkAvailable()) {
            sendMessage(NO_NETWORK)
            return
        }
        viewModelScope.launch {
            _isLoading.emit(true)
            homeRepository.searchShows(query)
                .onCompletion {
                    _isLoading.emit(false)
                }
                .collect()
        }
    }


    fun onSearch(text: String) {

        if (text.isEmpty()) {
            //fetch Trending shows  from api or db
            if(networkHelper.isNetworkAvailable())
                fetchShows()
            else {
                fetchShowsDb()
            }

        } else {
            searchShows(text)
        }

    }


}
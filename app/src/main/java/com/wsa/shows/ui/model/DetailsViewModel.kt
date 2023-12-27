package com.wsa.shows.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsa.shows.network.NetworkHelper
import com.wsa.shows.obj.ItemModel
import com.wsa.shows.repositories.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun isNetworkAvailable() = networkHelper.isNetworkAvailable()
    fun getException() = detailsRepository.error

    fun  hasFavorite()  = detailsRepository.hasFavorite

    fun setFavorite(seriesId: Int?){
        seriesId?.let {
            viewModelScope.launch {
                detailsRepository.hasFavoriteShow(seriesId)
            }

        }

    }

     fun getEpisodes(seriesId: Int?) =
            detailsRepository.getEpisodes(seriesId, 1)
             .onCompletion {
                 _isLoading.emit(false)
             }


    fun updateFavoriteStatus(itemModel: ItemModel){
        viewModelScope.launch {
            if(hasFavorite().value) {
                detailsRepository.removeFromFavorite(itemModel)
            }else{
                detailsRepository.addToFavorite(itemModel)

            }
        }
    }


}
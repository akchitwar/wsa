package com.wsa.shows.ui.fragments

import android.annotation.SuppressLint
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.wsa.shows.obj.EpisodeModel
import com.wsa.shows.obj.ItemModel
import com.wsa.shows.ui.component.AsyncImageComponent
import com.wsa.shows.ui.component.EpisodePagerComponent
import com.wsa.shows.ui.model.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val detailsViewModel by viewModels<DetailsViewModel>()
    private var mSelectedModel: ItemModel? = null
    private var episodesFlow = MutableStateFlow<List<EpisodeModel>>(listOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setData()
        detailsViewModel.setFavorite(mSelectedModel?.id)
        fetchEpisodes(mSelectedModel?.id)
    }

    private fun setData() {
        if (arguments != null
            && requireArguments().containsKey("selected_item")
        ) {
            mSelectedModel =
                if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU)
                    requireArguments().getParcelable("selected_item", ItemModel::class.java)
                else
                    requireArguments().getParcelable("selected_item")

        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold {

                    SetContents(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )

                }

            }
        }
    }

    private fun fetchEpisodes(id: Int?) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (detailsViewModel.isNetworkAvailable()) {
                    detailsViewModel.getEpisodes(id).collect {
                        episodesFlow.emit(it)
                    }
                }
            }
        }

    }


    @Composable
    fun SetContents(modifier: Modifier = Modifier) {
        Box(contentAlignment = Alignment.TopEnd) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                showDescription(
                    Modifier
                        .wrapContentWidth()
                        .fillMaxWidth()
                )

                ShowEpisodes(
                    Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                )
            }

            Surface(
                shape = CircleShape,
                modifier = Modifier
                    .padding(12.dp)
                    .size(32.dp)
                    .fillMaxWidth(),
                color = Color(0x77000000)
            ) {
                FavoriteButton()
            }
        }
    }

    @Composable
    private fun ShowEpisodes(modifier: Modifier = Modifier) {
         val episodeList = episodesFlow.collectAsState().value
        Column(
            modifier = modifier
        ) {
            EpisodePagerComponent.HorizontalPager(
                episodeList,
                Modifier.fillMaxWidth().fillMaxWidth()
            )
        }
    }

    @Composable
    private fun showDescription(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
        ) {
            AsyncImageComponent.ImageFromURLWithPlaceHolder(
                Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth(),
                mSelectedModel?.posterPath, ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = "Description",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
                fontSize = 16.sp
            )

            Text(
                text = mSelectedModel?.overview ?: "",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }

    @Composable
    fun FavoriteButton(
        modifier: Modifier = Modifier,
        color: Color = Color.White
    ) {
        val isFavorite = detailsViewModel.hasFavorite().collectAsState()

        IconToggleButton(
            checked = isFavorite.value,
            modifier = modifier,
            onCheckedChange = {
                mSelectedModel?.let {
                    detailsViewModel.updateFavoriteStatus(it)
                }

            }

        ) {
            Icon(
                tint = color,
                imageVector = if (isFavorite.value) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = null
            )
        }

    }


    @Preview(showBackground = true)
    @Composable
    fun previewImage() {
        SetContents(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        )
    }


}
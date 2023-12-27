package com.wsa.shows.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wsa.shows.obj.EpisodeModel

/*Component is using to show Episode View pager*/
object EpisodePagerComponent {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun HorizontalPager(episodeList: List<EpisodeModel>, modifier: Modifier) {

        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) {
            episodeList.count()
        }
        Column(modifier = modifier) {

            HorizontalPager(
                modifier = Modifier.height(120.dp),
                state = pagerState,
                userScrollEnabled = true,
                reverseLayout = false,
                contentPadding = PaddingValues(horizontal = 10.dp),
                beyondBoundsPageCount = 0,
                pageSize = PageSize.Fixed(150.dp),
                key = null,
                pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                    Orientation.Horizontal
                ),
                pageContent = { ShowImageWithTitle(Modifier.padding(2.dp)
                    ,episodeModel = episodeList[it]) }
            )
        }
    }

    @Composable
    private fun ShowImageWithTitle(modifier: Modifier = Modifier, episodeModel: EpisodeModel?) {

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = modifier,
            elevation = CardDefaults. cardElevation(2.dp),
            shape = RectangleShape
            ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Episode ${episodeModel?.episodeNumber}",
                    modifier = Modifier.padding(4.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                AsyncImageComponent.ImageFromURLWithPlaceHolder(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(5.dp)
                        .weight(0.4f, true),
                    episodeModel?.stillPath, ContentScale.FillBounds
                )

            }
        }
    }


}
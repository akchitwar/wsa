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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wsa.shows.obj.ItemModel

/*Component is using to show Favorite shows View pager inside Home screen*/
object FavoritePagerComponent {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun HorizontalPager(items: List<ItemModel>, modifier: Modifier, onClick: (item: ItemModel)->Unit) {
        if(items.isEmpty())return
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) {
            items.count()
        }
        Column(modifier = modifier) {
            Text(
                text = "Favorite Shows",
                modifier = Modifier
                 .testTag("favorite_text")
                .padding(PaddingValues(start = 8.dp, top = 4.dp, bottom = 0.4.dp)),
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            HorizontalPager(
                modifier = Modifier
                    .height(120.dp)
                    .testTag("view_pager"),
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
                    ,itemModel = items[it], onClick) }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ShowImageWithTitle(modifier: Modifier = Modifier, itemModel: ItemModel, onClick: (item: ItemModel)->Unit) {

        Card(
            onClick = {
                onClick(itemModel)
            },
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = modifier,
            elevation = CardDefaults. cardElevation(2.dp),
            shape = RectangleShape,

            ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                AsyncImageComponent.ImageFromURLWithPlaceHolder(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(5.dp)
                        .weight(0.4f, true)
                        .testTag("favorite_image"),
                    itemModel.posterPath, ContentScale.FillBounds
                )

                Text(
                    text = "${itemModel.name}",
                    modifier = Modifier.padding(4.dp).testTag("series_text"),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 14.sp,
                    maxLines = 1,
                    softWrap = true,
                    textAlign = TextAlign.Center
                )

            }
        }
    }


}
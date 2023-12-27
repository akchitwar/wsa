package com.wsa.shows.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wsa.shows.obj.ItemModel

/*Component is using to show Trending shows as Grid format inside Home screen*/
object GridViewComponent {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowGridView(
        modifier: Modifier = Modifier,
        gridList: List<ItemModel>, onClick: (item: ItemModel) -> Unit
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = modifier,
        ) {

            items(gridList.size) {
                val itemModel = gridList[it]

                Card(
                    onClick = {
                        onClick(itemModel)
                    },
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.padding(4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)

                ) {
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(5.dp),

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImageComponent.ImageFromURLWithPlaceHolder(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(2.dp), itemModel.posterPath
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = itemModel.name ?: "",
                            modifier = Modifier.padding(2.dp),
                            maxLines = 2,
                            softWrap = true,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
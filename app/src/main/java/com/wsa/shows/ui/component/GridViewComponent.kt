package com.wsa.shows.ui.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wsa.shows.R
import com.wsa.shows.obj.ItemModel


object GridViewComponent {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
     fun ShowGridView(
        gridList: State<List<ItemModel>>,
        context: Context
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(5.dp),

            ) {

            items(gridList.value.size) {
                val itemModel = gridList.value[it]
                Card(

                    onClick = {
                        Toast.makeText(context, " clicked..", Toast.LENGTH_SHORT).show()
                    },

                    modifier = Modifier.padding(8.dp),
                    elevation = CardDefaults.cardElevation(2.dp)

                ) {
                    // on below line we are creating a column on below sides.
                    Column(
                        // on below line we are adding padding
                        // padding for our column and filling the max size.
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(5.dp),

                        // on below line we are adding
                        // horizontal alignment for our column
                        horizontalAlignment = Alignment.CenterHorizontally,

                        // on below line we are adding
                        // vertical arrangement for our column
                        verticalArrangement = Arrangement.Center
                    ) {
                        // on below line we are creating image for our grid view item.
                        Image(
                            // on below line we are specifying the drawable image for our image.
                            //   painter = painterResource(id = courseList[it].languageImg),
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            // on below line we are specifying
                            // content description for our image
                            contentDescription = itemModel.overview,
                            contentScale = ContentScale.Fit,
                            // on below line we are setting height
                            // and width for our image.
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(5.dp)
                        )

                        // on the below line we are adding a spacer.
                        Spacer(modifier = Modifier.height(9.dp))

                        // on below line we are creating
                        // a text for our grid view item
                        Text(
                            // inside the text on below line we are
                            // setting text as the language name
                            // from our modal class.
                            text = itemModel.originalName ?: "",

                            // on below line we are adding padding
                            // for our text from all sides.
                            modifier = Modifier.padding(4.dp),
                            maxLines = 2,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic,
                            // on below line we are
                            // adding color for our text
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
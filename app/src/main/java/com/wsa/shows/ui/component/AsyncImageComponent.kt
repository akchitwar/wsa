package com.wsa.shows.ui.component


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wsa.shows.R
import com.wsa.shows.network.api.ApiConstants

object AsyncImageComponent {
    @Composable
    fun ImageFromURLWithPlaceHolder(
        modifier: Modifier, imageUrl: String?,
        contentScale: ContentScale = ContentScale.Fit
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ApiConstants.IMAGE_PREFIX_URL.plus(imageUrl))
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_load),
            contentDescription = stringResource(R.string.app_name),
            error = painterResource(R.drawable.ic_no_image),
            contentScale = contentScale,

            modifier = modifier

        )


    }
}
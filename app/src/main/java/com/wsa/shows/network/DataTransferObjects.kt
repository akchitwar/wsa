/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wsa.shows.network

import com.wsa.shows.network.response.TrendingResponse
import com.wsa.shows.db.entities.TrShowEntity

fun TrendingResponse.asDatabaseModel( ): List<TrShowEntity> {
    return results!!.map {
        TrShowEntity(
            autoId = 0,
            id = it?.id,
            title = it?.title,
            originalTitle = it?.originalTitle,
            originalLanguage = it?.originalLanguage,
            originalName = it?.originalName,
            posterPath = it?.posterPath,
            backdropPath = it?.backdropPath,
            mediaType = it?.mediaType,
            overview = it?.overview

        )
    }
}






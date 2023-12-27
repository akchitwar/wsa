package com.wsa.shows.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wsa.shows.obj.ItemModel
import com.wsa.shows.ui.component.FavoritePagerComponent.HorizontalPager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EpisodePagerComponentTest {

    @get: Rule
    val composeTestRule = createComposeRule()
    @Before
    fun setUp() {
        composeTestRule
        composeTestRule.setContent {
            val itemModels = listOf( ItemModel(1,"Testing overview", "Episode 1"))
            HorizontalPager(itemModels, Modifier.fillMaxWidth(), {})
        }
    }

    @Test
    fun verify_if_all_views_exists() {
        composeTestRule.onAllNodesWithTag("series_text")
        composeTestRule.onAllNodesWithTag("favorite_image")
        composeTestRule.onAllNodes(hasText("Testing overview"))  // onNodeWithText().assertIsDisplayed()
        composeTestRule.onAllNodes(hasText("Episode 1"))

    }
}


package com.unken.codewars.challenges.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ramcosta.composedestinations.DestinationsNavHost
import com.unken.codewars.challenges.data.repository.FakeCodeWarRepositoryCaseImpl
import com.unken.codewars.common.presentation.MainActivity
import com.unken.codewars.common.utils.Constants.ChallengesListTag
import com.unken.codewars.common.utils.Constants.StartButtonTag
import com.unken.codewars.common.utils.Constants.EndButtonTag
import com.unken.codewars.common.utils.Constants.PreviousButtonTag
import com.unken.codewars.common.utils.Constants.NextButtonTag
import com.unken.codewars.common.utils.Constants.PageTextFieldTag
import com.unken.codewars.ui.theme.CodewarsTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ChallengesListScreenTest {

    @get:Rule(order = 1)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        composeTestRule.setContent {
            CodewarsTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root)
            }
        }
    }


    @Test
    fun challenges_list_is_rendered_correctly() = runBlocking {
        delay(600L)

        // Assert the list is displayed
        composeTestRule.onNodeWithTag(ChallengesListTag).assertIsDisplayed()

        // Retrieve challenges from the first page
        // We will check if they are displayed in the list
        val expectedListItems = FakeCodeWarRepositoryCaseImpl.challenges[0]

        // Assert all expected items exist within the tree
        expectedListItems.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertExists()
        }
    }

    @Test
    fun navigates_to_challenge_details_screen_on_list_item_click(): Unit = runBlocking {
        delay(600L)

        // Retrieve an item that is expected to be in the list
        val expectedItem = FakeCodeWarRepositoryCaseImpl.challenges[0].first()

        // Click on the first item in the current list
        composeTestRule.onNodeWithText(expectedItem.name).performClick()

        delay(100L)

        // Check that the item's description is displayed
        composeTestRule.onNodeWithText(expectedItem.name).assertIsDisplayed()

    }

    @Test
    fun disables_start_and_previous_controls_when_on_first_page(): Unit = runBlocking {
        delay(600L)

        composeTestRule.onNodeWithTag(StartButtonTag).assertIsNotEnabled()
        composeTestRule.onNodeWithTag(PreviousButtonTag).assertIsNotEnabled()
    }

    @Test
    fun disables_end_and_next_controls_when_on_last_page() : Unit = runBlocking {
        delay(600L)

        // Go to the last page
        composeTestRule.onNodeWithTag(EndButtonTag).performClick()

        delay(600L)

        composeTestRule.onNodeWithTag(EndButtonTag).assertIsNotEnabled()
        composeTestRule.onNodeWithTag(NextButtonTag).assertIsNotEnabled()
    }

    @Test
    fun fetches_next_page_on_next_button_click() : Unit = runBlocking {
        delay(600L)

        // Go to the next page, from first page
        composeTestRule.onNodeWithTag(NextButtonTag).performClick()

        delay(600L)

        // Assert that we are now on page two
        // Check that page two data is displayed
        val expectedListItems = FakeCodeWarRepositoryCaseImpl.challenges[1]
        expectedListItems.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertExists()
        }

    }

    @Test
    fun fetches_prev_page_on_prev_button_click()  = runBlocking {
        delay(600L)

        // Go to last page, so that we can have the previous button enabled
        composeTestRule.onNodeWithTag(EndButtonTag).performClick()

        delay(600L)

        // Now click on the previous button
        composeTestRule.onNodeWithTag(PreviousButtonTag).performClick()

        delay(600L)

        // Assert that we are now on page two
        // Check that page two data is displayed
        val expectedListItems = FakeCodeWarRepositoryCaseImpl.challenges[1]
        expectedListItems.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertExists()
        }
    }

    @Test
    fun fetches_first_page_on_start_button_click() : Unit = runBlocking {
        delay(600L)

        // Go to last page, so that we can have the previous button enabled
        composeTestRule.onNodeWithTag(EndButtonTag).performClick()

        delay(600L)

        composeTestRule.onNodeWithTag(StartButtonTag).performClick()

        delay(600L)

        // Retrieve challenges from the first page
        // We will check if they are displayed in the list
        val expectedListItems = FakeCodeWarRepositoryCaseImpl.challenges[0]

        // Assert all expected items exist within the tree
        expectedListItems.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertExists()
        }
    }

    @Test
    fun fetches_last_page_on_end_button_click() : Unit = runBlocking {
        delay(600L)

        // Go to last page, so that we can have the previous button enabled
        composeTestRule.onNodeWithTag(EndButtonTag).performClick()

        delay(600L)

        // Retrieve challenges from the first page
        // We will check if they are displayed in the list
        val expectedListItems = FakeCodeWarRepositoryCaseImpl.challenges[2]

        // Assert all expected items exist within the tree
        expectedListItems.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertExists()
        }
    }

    @Test
    fun loads_correct_page_when_a_valid_page_number_is_entered_in_page_search_field() : Unit = runBlocking {
        delay(600L)

        // Go to last page, by type out the page number in the search field
        composeTestRule.onNodeWithTag(PageTextFieldTag).performTextReplacement("3")

        delay(600L)

        val expectedListItems = FakeCodeWarRepositoryCaseImpl.challenges[2]

        // Assert all expected items exist within the tree
        expectedListItems.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertExists()
        }
    }

    @Test
    fun does_not_fetch_challenges_when_an_invalid_page_number_is_entered() : Unit = runBlocking {
        delay(600L)

        // Assert that the items displayed are from page one
        val expectedListItems = FakeCodeWarRepositoryCaseImpl.challenges[0]
        expectedListItems.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertExists()
        }

        // Type in a page number that is above the range
        composeTestRule.onNodeWithTag(PageTextFieldTag).performTextReplacement("100")

        delay(600L)

        // Assert that the items in display have not changed
        expectedListItems.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertExists()
        }

        // Type in a page number that is above the range
        composeTestRule.onNodeWithTag(PageTextFieldTag).performTextReplacement("")

        delay(600L)

        // Assert that the items in display have not changed
        expectedListItems.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertExists()
        }
    }




}
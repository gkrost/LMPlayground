package com.druk.lmplayground

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.druk.lmplayground.conversation.ConversationBarTestTag
import org.junit.Rule
import org.junit.Test

class ConversationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAppLaunch() {
        // Check that the conversation screen is visible on launch
        composeTestRule.onNodeWithTag(ConversationBarTestTag).assertIsDisplayed()
    }

    @Test
    fun testSendMessage() {
        val message = "Hello"
        composeTestRule.onNodeWithContentDescription("Text input")
            .performTextInput(message)
        composeTestRule.onNodeWithText("Send").performClick()
        composeTestRule.onNodeWithText(message).assertIsDisplayed()
    }
}
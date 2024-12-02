package com.druk.lmplayground.conversation

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.druk.lmplayground.R
import kotlinx.coroutines.launch

@Composable
fun Messages(
    messages: List<Message>,
    navigateToProfile: (String) -> Unit,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    var piningBottom by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Box(modifier = modifier.pointerInput(Unit) {
        detectTapGestures(onPress = {
            piningBottom = false
        })
    }) {
        val authorMe = stringResource(id = R.string.author_me)
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize()
        ) {
            for (index in messages.indices) {
                val content = messages[index]
                item {
                    Message(
                        onAuthorClick = { name -> navigateToProfile(name) },
                        msg = content,
                        isUserMe = content.author == authorMe,
                        isFirstMessageByAuthor = true,
                        isLastMessageByAuthor = true
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Show the button if the first visible item is not the first one or if the offset is
        // greater than the threshold.
        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.canScrollForward
            }
        }

        // Auto scrolling to the latest item
        LaunchedEffect(piningBottom, scrollState.canScrollForward) {
            if (scrollState.canScrollForward && piningBottom) {
                scope.launch {
                    val totalItemsCount = scrollState.layoutInfo.totalItemsCount
                    scrollState.scrollToItem(totalItemsCount - 1)
                }
            }
        }

        JumpToBottom(
            // Only show if the scroller is not at the bottom
            enabled = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    val totalItemsCount = scrollState.layoutInfo.totalItemsCount
                    scrollState.animateScrollToItem(totalItemsCount - 1)
                    piningBottom = true
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
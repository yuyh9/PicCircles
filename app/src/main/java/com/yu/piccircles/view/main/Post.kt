package com.yu.piccircles.view.main

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.yu.piccircles.PicViewModel
import com.yu.piccircles.data.PostData
import com.yu.piccircles.view.CommonImage
import com.yu.piccircles.view.LikeAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Post(post: PostData, currentUserId: String, vm: PicViewModel, onPostClick: () -> Unit) {
    val likeAnimation = remember { mutableStateOf(false) }
    val dislikeAnimation = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        shape = RoundedCornerShape(corner = CornerSize(4.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp, bottom = 4.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = CircleShape, modifier = Modifier
                        .padding(4.dp)
                        .size(32.dp)
                ) {
                    CommonImage(data = post.userImage, contentScale = ContentScale.Crop)
                }
                Text(text = post.username ?: "", modifier = Modifier.padding(4.dp))
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val imageModifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 150.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                coroutineScope.launch {
                                    if (post.likes?.contains(currentUserId) == true) {
                                        dislikeAnimation.value = true
                                    } else {
                                        likeAnimation.value = true
                                    }
                                    vm.onLikePost(post)
                                    delay(1000)
                                    likeAnimation.value = false
                                    dislikeAnimation.value = false
                                }
                            },
                            onTap = { onPostClick.invoke() }
                        )
                    }
                CommonImage(data = post.postImage,
                    modifier = imageModifier,
                    contentScale = ContentScale.FillWidth)

                if (likeAnimation.value) {
                    LikeAnimation()
                }
                if (dislikeAnimation.value) {
                    LikeAnimation(false)
                }
            }
        }
    }
}
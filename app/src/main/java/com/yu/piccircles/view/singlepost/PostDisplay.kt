package com.yu.piccircles.view.singlepost

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.yu.piccircles.PicViewModel
import com.yu.piccircles.R
import com.yu.piccircles.data.PostData
import com.yu.piccircles.view.CommonImage
import com.yu.piccircles.view.DestinationScreen
import com.yu.piccircles.view.LikeAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SinglePostDisplay(navController: NavController, vm: PicViewModel, post: PostData, nbComments: Int) {
    val userData = vm.userData.value
    val likeAnimation = remember { mutableStateOf(false) }
    val dislikeAnimation = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = post.userImage),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = post.username ?: "", modifier = Modifier.weight(1f))

            if(userData?.userId == post.userId) {
                // current user's post. Don't show anything
            }else if(userData?.following?.contains(post.userId) == true){
                Text(
                    text = "Following",
                    color = Color.Gray,
                    modifier = Modifier.clickable { vm.onFollowClick(post.userId!!) }
                )
            }else{
                Text(
                    text = "Follow",
                    color = Color.Blue,
                    modifier = Modifier.clickable { vm.onFollowClick(post.userId!!) }
                )
            }
        }

        val imageModifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 150.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        coroutineScope.launch {
                            if (post.likes?.contains(userData?.userId) == true) {
                                dislikeAnimation.value = true
                            } else {
                                likeAnimation.value = true
                            }
                            vm.onLikePost(post)
                            delay(1000)
                            likeAnimation.value = false
                            dislikeAnimation.value = false
                        }
                    }
                )
            }

        Box(modifier = Modifier.fillMaxWidth()) {
            CommonImage(
                data = post.postImage,
                modifier = imageModifier,
                contentScale = ContentScale.FillWidth
            )
            if (likeAnimation.value || dislikeAnimation.value) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LikeAnimation(!dislikeAnimation.value)
                }
            }
        }

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.p_like),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Red)
            )
            Text(text = " ${post.likes?.size ?: 0} likes", modifier = Modifier.padding(start = 0.dp))
        }

        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = post.username ?: "", fontWeight = FontWeight.Bold)
            Text(text = post.postDescription ?: "", modifier = Modifier.padding(start = 8.dp))
        }

        Row(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "$nbComments comments",
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        post.postId?.let {
                            navController.navigate(DestinationScreen.Comments.createRoute(it))
                        }
                    }
            )
        }
    }
}

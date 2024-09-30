package com.yu.piccircles.view.singlepost

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yu.piccircles.PicViewModel
import com.yu.piccircles.data.PostData
import com.yu.piccircles.view.CommonDivider

@Composable
fun SinglePostScreen(navController: NavController, vm: PicViewModel, post: PostData) {
    val comments = vm.comments.value
    LaunchedEffect(key1 = Unit) {
        vm.getComments(post.postId)
    }

    post.userId?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Back", modifier = Modifier.clickable { navController.popBackStack() })
            CommonDivider()
            SinglePostDisplay(navController = navController, vm = vm, post = post, nbComments = comments.size)
        }
    }
}


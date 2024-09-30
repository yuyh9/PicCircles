package com.yu.piccircles.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.yu.piccircles.view.DestinationScreen
import com.yu.piccircles.PicViewModel
import com.yu.piccircles.data.PostData
import com.yu.piccircles.view.CommonProgressSpinner
import com.yu.piccircles.view.NavParam
import com.yu.piccircles.view.navigateTo

@Composable
fun PostsList(
    posts: List<PostData>,
    modifier: Modifier,
    loading: Boolean,
    navController: NavController,
    vm: PicViewModel,
    currentUserId: String
) {
    Box(modifier = modifier){
        LazyColumn{
            items(items = posts){
                Post(
                    post = it,
                    currentUserId = currentUserId,
                    vm = vm
                ){
                    navigateTo(
                        navController,
                        DestinationScreen.SinglePost,
                        NavParam("post", it)
                    )
                }
            }
        }
        if(loading)
            CommonProgressSpinner()
    }
}
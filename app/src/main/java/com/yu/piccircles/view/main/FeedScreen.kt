package com.yu.piccircles.view.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.yu.piccircles.PicViewModel
import com.yu.piccircles.R
import com.yu.piccircles.view.BottomNavigationItem
import com.yu.piccircles.view.BottomNavigationMenu

@Composable
fun FeedScreen(navController: NavController, vm: PicViewModel){
    val userDataLoading = vm.inProgress.value
    val userData = vm.userData.value
    val personalizedFeed = vm.postsFeed.value
    val personalizedFeedLoading = vm.postsFeedProgress.value

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White),
            horizontalArrangement = Arrangement.Center
        ){
            val logoPainter = painterResource(id = R.drawable.logo)
            Image(
                painter = logoPainter,
                contentDescription = "App Logo",

            )
        }
        PostsList(
            posts = personalizedFeed,
            modifier = Modifier.weight(1f),
            loading = personalizedFeedLoading or userDataLoading,
            navController = navController,
            vm = vm,
            currentUserId = userData?.userId ?: ""
        )
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.FEED,
            navController = navController
        )
    }
}

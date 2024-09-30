package com.yu.piccircles.view.posts

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yu.piccircles.view.DestinationScreen
import com.yu.piccircles.PicViewModel
import com.yu.piccircles.view.BottomNavigationItem
import com.yu.piccircles.view.BottomNavigationMenu
import com.yu.piccircles.view.CommonProgressSpinner
import com.yu.piccircles.view.NavParam
import com.yu.piccircles.view.navigateTo

@Composable
fun MyPostsScreen(navController: NavController, vm: PicViewModel){
    val newPostImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ){uri ->
        uri?.let{
            val encoded = Uri.encode(it.toString())
            val route = DestinationScreen.NewPost.createRoute(encoded)
            navController.navigate(route)
        }
    }

    val userData = vm.userData.value
    val isLoading = vm.inProgress.value
    val postsLoading = vm.refreshPostsProgress.value
    val posts = vm.posts.value
    val followerSize = vm.followersSize.value

    Column {
        Column(modifier = Modifier.weight(1f)){
            Row {
                ProfileImage(userData?.imageUrl){
                    newPostImageLauncher.launch("image/*")
                }
                Text(
                    text = "${posts.size}\nposts",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$followerSize\nfollowers",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${userData?.following?.size ?: 0}\nfollowing",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.padding(8.dp)){
                val usernameDisplay = if(userData?.username == null) "" else "@${userData?.username}"
                Text(text = userData?.name ?: "", fontWeight = FontWeight.Bold)
                Text(text = usernameDisplay)
                Text(text = userData?.bio ?: "")
            }
            OutlinedButton(
                onClick = { navigateTo(navController, DestinationScreen.Profile) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                shape = RoundedCornerShape(10)
            ){
                Text(text = "Edit Profile", color = Color.Black)
            }
            PostList(
                isContextLoading = isLoading,
                postsLoading = postsLoading,
                posts = posts,
                modifier = Modifier.weight(1f).padding(1.dp).fillMaxSize()
            ){ post ->
                navigateTo(
                    navController = navController,
                    DestinationScreen.SinglePost,
                    NavParam("post", post)
                )
            }
        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.POSTS,
            navController = navController
        )
    }
    if(isLoading){
        CommonProgressSpinner()
    }
}

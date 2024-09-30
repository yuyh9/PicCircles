package com.yu.piccircles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yu.piccircles.data.PostData
import com.yu.piccircles.ui.theme.PicCirclesTheme
import com.yu.piccircles.view.Comment.CommentsScreen
import com.yu.piccircles.view.DestinationScreen
import com.yu.piccircles.view.NotificationManager
import com.yu.piccircles.view.auth.LoginScreen
import com.yu.piccircles.view.auth.SignupScreen
import com.yu.piccircles.view.main.FeedScreen
import com.yu.piccircles.view.posts.MyPostsScreen
import com.yu.piccircles.view.posts.NewPostScreen
import com.yu.piccircles.view.profile.ProfileScreen
import com.yu.piccircles.view.search.SearchScreen
import com.yu.piccircles.view.singlepost.SinglePostScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PicCirclesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PicCirclesApp()
                }
            }
        }
    }
}

@Composable
fun PicCirclesApp() {
    val vm = hiltViewModel<PicViewModel>()
    val navController = rememberNavController()
    NotificationManager(vm = vm)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NavHost(
            navController = navController,
            startDestination = DestinationScreen.Login.route
        ) {
            composable(DestinationScreen.Signup.route) {
                SignupScreen(navController = navController, vm = vm)
            }
            composable(DestinationScreen.Login.route) {
                LoginScreen(navController = navController, vm = vm)
            }
            composable(DestinationScreen.Feed.route) {
                FeedScreen(navController = navController, vm = vm)
            }
            composable(DestinationScreen.Search.route){
                SearchScreen(navController = navController, vm = vm)
            }
            composable(DestinationScreen.MyPosts.route){
                MyPostsScreen(navController = navController, vm = vm)
            }
            composable(DestinationScreen.Profile.route){
                ProfileScreen(navController = navController, vm = vm)
            }
            composable(DestinationScreen.NewPost.route){ navBackStackEntry ->
                val imageUri = navBackStackEntry.arguments?.getString("imageUri")
                imageUri?.let{
                    NewPostScreen(navController = navController, vm = vm, encodedUri = it)
                }
            }
            composable(DestinationScreen.SinglePost.route){
                val postData = navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<PostData>("post")

                postData?.let {
                    SinglePostScreen(
                        navController = navController,
                        vm = vm,
                        post = postData
                    )
                }
            }
            composable(DestinationScreen.Comments.route){ navBackStackEntry ->
                val postId = navBackStackEntry.arguments?.getString("postId")
                postId?.let { CommentsScreen(navController = navController, vm = vm, postId = it) }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PicCirclesTheme {
        PicCirclesApp()
    }
}



package com.yu.piccircles.view.search


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yu.piccircles.view.DestinationScreen
import com.yu.piccircles.PicViewModel
import com.yu.piccircles.view.BottomNavigationItem
import com.yu.piccircles.view.BottomNavigationMenu
import com.yu.piccircles.view.NavParam
import com.yu.piccircles.view.navigateTo
import com.yu.piccircles.view.posts.PostList

@Composable
fun SearchScreen(navController: NavController, vm: PicViewModel) {
    val searchedPostsLoading = vm.searchedPostsProgress.value
    val searchedPosts = vm.searchedPosts.value
    var searchTerm by rememberSaveable { mutableStateOf("") }

    Column{
        SearchBar(
            searchTerm = searchTerm,
            onSearchChange = { searchTerm = it },
            onSearch = { vm.searchPosts(searchTerm) }
        )
        PostList(
            isContextLoading = false,
            postsLoading = searchedPostsLoading,
            posts = searchedPosts,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ){ post ->
            navigateTo(
                navController = navController,
                DestinationScreen.SinglePost,
                NavParam("post", post)
            )
        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.SEARCH,
            navController = navController
        )
    }
}


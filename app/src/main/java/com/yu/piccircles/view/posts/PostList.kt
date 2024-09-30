package com.yu.piccircles.view.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yu.piccircles.data.PostData
import com.yu.piccircles.data.PostRow
import com.yu.piccircles.view.CommonProgressSpinner

@Composable
fun PostList(
    isContextLoading: Boolean,
    postsLoading: Boolean,
    posts: List<PostData>,
    modifier: Modifier,
    onPostClick: (PostData) -> Unit
) {
    if(postsLoading){
        CommonProgressSpinner()
    }else if(posts.isEmpty()){
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            if(!isContextLoading) Text(text = "No posts available")
        }
    }else{
        LazyColumn(modifier = modifier){
            val rows = arrayListOf<PostRow>()
            var currentRow = PostRow()
            rows.add(currentRow)
            for(post in posts){
                if(currentRow.isFull()){
                    currentRow = PostRow()
                    rows.add(currentRow)
                }
                currentRow.add(post = post)
            }
            items(items = rows){row ->
                PostsRow(item = row, onPostClick = onPostClick)
            }
        }
    }
}

@Composable
fun PostsRow(item: PostRow, onPostClick: (PostData) -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)){
        PostImage(imageUrl = item.post1?.postImage, modifier = Modifier
            .weight(1f)
            .clickable { item.post1?.let { post -> onPostClick(post) } }
        )
        PostImage(imageUrl = item.post2?.postImage, modifier = Modifier
            .weight(1f)
            .clickable { item.post2?.let { post -> onPostClick(post) } }
        )
        PostImage(imageUrl = item.post3?.postImage, modifier = Modifier
            .weight(1f)
            .clickable { item.post3?.let { post -> onPostClick(post) } }
        )
    }
}
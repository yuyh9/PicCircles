package com.yu.piccircles.view.Comment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yu.piccircles.data.CommentData

@Composable
fun CommentRow(comment: CommentData, onDeleteComment: (String) -> Unit, currentUsername: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = comment.username ?: "", fontWeight = FontWeight.Bold)
            Text(text = comment.text ?: "", modifier = Modifier.padding(start = 8.dp))
        }

        if (comment.username == currentUsername) {
            IconButton(onClick = { onDeleteComment(comment.commentId ?: "") }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Comment", tint = Color.Gray)
            }
        }
    }
}

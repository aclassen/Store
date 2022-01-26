/*
 * Copyright 2022 André Claßen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.burnoutcrew.storekmp.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.burnoutcrew.storekmp.android.ui.theme.MyApplicationTheme
import org.burnoutcrew.storekmp.repositories.Post

@Composable
fun PostsList(modifier: Modifier = Modifier, posts: List<Post>, isLoading: Boolean, onRefresh: () -> Unit) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoading),
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        LazyColumn {
            items(posts, { it.id }) { post ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(post.title, style = MaterialTheme.typography.h6)
                    Text(
                        post.description,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Divider()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        PostsList(posts = List(10) {
            Post(
                id = it.toString(),
                title = "title $it",
                text = if (it % 2 == 0) "Row 1" else "Row 1\nRow 2",
                url = "http://"
            )
        }, isLoading = false, onRefresh = {})
    }
}

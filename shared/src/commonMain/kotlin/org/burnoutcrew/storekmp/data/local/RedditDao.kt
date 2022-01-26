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

package org.burnoutcrew.storekmp.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import org.burnoutcrew.storekmp.sqldelight.data.Database
import org.burnoutcrew.storekmp.sqldelight.data.PostEntity

internal class RedditDao(private val database: Database) {
    fun clearFeedBySubredditName(subredditName: String) {
        database.redditFeedQueries.deleteFeedBySubredditName(subredditName)
    }

    fun clearAllFeeds() {
        database.redditFeedQueries.deleteAllFeeds()
    }

    fun insertPosts(subredditName: String, posts: List<PostEntity>) {
        database.redditFeedQueries.transaction {
            database.redditFeedQueries.deleteFeedBySubredditName(subredditName)
            posts.forEachIndexed { index, post ->
                database.redditFeedQueries.insertFeed(
                    subredditName = subredditName,
                    postOrder = index.toLong(),
                    postId = post.id
                )
            }
            posts.forEach { post ->
                database.redditFeedQueries.insertPost(
                    post.id,
                    post.title,
                    post.text,
                    post.url
                )
            }
            database.redditFeedQueries.deleteObsoletePosts()
        }
    }

    fun loadPosts(subreddit: String): Flow<List<PostEntity>> =
        database.redditFeedQueries.selectBySubredditName(subreddit) { id: String, tile: String, text: String?, url: String ->
            PostEntity(id, text, tile, url)
        }
            .asFlow()
            .mapToList()

}
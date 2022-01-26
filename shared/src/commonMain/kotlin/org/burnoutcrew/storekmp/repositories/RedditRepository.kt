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

package org.burnoutcrew.storekmp.repositories

import com.dropbox.kmp.external.fs3.FileSystemPersister
import com.dropbox.kmp.external.fs3.filesystem.FileSystem
import com.dropbox.kmp.external.store4.Fetcher
import com.dropbox.kmp.external.store4.MemoryPolicy
import com.dropbox.kmp.external.store4.SourceOfTruth
import com.dropbox.kmp.external.store4.Store
import com.dropbox.kmp.external.store4.StoreBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.Buffer
import okio.use
import org.burnoutcrew.storekmp.data.local.RedditDao
import org.burnoutcrew.storekmp.data.remote.DataFetcher
import org.burnoutcrew.storekmp.data.remote.RedditApiData
import org.burnoutcrew.storekmp.sqldelight.data.PostEntity
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

internal class RedditRepository(private val fetcher: DataFetcher, private val redditDao: RedditDao, fileSystem: FileSystem) {
    val subredditStore: Store<String, List<Post>> = StoreBuilder.from(
        Fetcher.of { key: String -> fetcher.fetchLatestSubreddit(key).mapToPost() },
        sourceOfTruth = SourceOfTruth.of(
            reader = { key -> redditDao.loadPosts(key).map { posts -> posts.map { Post(it.id, it.title, it.text, it.url) } } },
            writer = { key, posts -> redditDao.insertPosts(key, posts.map { PostEntity(it.id, it.text, it.title, it.url) }) },
            delete = redditDao::clearFeedBySubredditName,
            deleteAll = redditDao::clearAllFeeds
        )
    )
        .build()

    @ExperimentalTime
    val bestSubredditsStore: Store<Unit, List<Post>> =
        with(FileSystemPersister.create<Unit>(fileSystem) { "best.json" }) {
            StoreBuilder
                .from<Unit, RedditApiData, List<Post>>(
                    Fetcher.of { fetcher.fetchBestSubreddits() },
                    sourceOfTruth = SourceOfTruth.of(
                        nonFlowReader = { key ->
                            runCatching {
                                this.read(key)?.use {
                                    json.decodeFromString<RedditApiData>(it.readUtf8())
                                }?.mapToPost()
                            }.getOrNull()
                        },
                        writer = { key, config ->
                            val buffer = withContext(Dispatchers.Default) {
                                Buffer().writeUtf8(json.encodeToString(config))
                            }
                            this.write(key, buffer)
                        }
                    )
                )
                .cachePolicy(MemoryPolicy.builder<Any, Any>().setExpireAfterWrite(10.seconds).build())
                .build()
        }

    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
        }
    }
}

private fun RedditApiData.mapToPost() =
    data.children
        .map { post -> Post(post.data.id, post.data.title, post.data.selftext, post.data.url) }
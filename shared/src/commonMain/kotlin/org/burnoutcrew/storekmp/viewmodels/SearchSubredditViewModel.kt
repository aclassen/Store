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

package org.burnoutcrew.storekmp.viewmodels

import com.dropbox.kmp.external.store4.StoreRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import org.burnoutcrew.storekmp.repositories.Post
import org.burnoutcrew.storekmp.repositories.RedditRepository
import org.burnoutcrew.storekmp.platform.CommonViewModel
import org.burnoutcrew.storekmp.viewmodels.comm.AsyncResource
import org.burnoutcrew.storekmp.viewmodels.comm.runningAsyncResource
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
open class SearchSubredditViewModel internal constructor(repository: RedditRepository) : CommonViewModel() {
    private val keyFlow = MutableStateFlow(Query("aww"))

    var query: String
        get() = keyFlow.value.query
        set(value) {
            keyFlow.value = Query(value)
        }

    val feed: Flow<AsyncResource<List<Post>?>> =
        keyFlow.flatMapLatest { key ->
            repository.subredditStore
                .stream(StoreRequest.cached(key.query, true))
                .runningAsyncResource()
        }

    fun refresh() {
        query = query
    }

    // As a MutableStateFlow only emits if the value is not equal , wrap the query so we can easy trigger a refresh
    // - A own refresh channel, in this case refresh via query would not work and needs
    // extra logic (user press send without a text change)
    private class Query(val query: String)
}

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

package org.burnoutcrew.storekmp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import org.burnoutcrew.storekmp.viewmodels.SearchSubredditViewModel
import org.burnoutcrew.storekmp.viewmodels.comm.AsyncResource

@ExperimentalCoroutinesApi
class SearchSubredditViewModelios constructor(private val viewModel: SearchSubredditViewModel) {
    val feed = viewModel.feed.map { res -> AsyncResource(res.value?.let { Posts(it) }, res.state) }.wrap(viewModel.viewModelScope)
    var query by viewModel::query
    fun refresh() = viewModel.refresh()
    fun onCleared() = viewModel.onCleared()
}


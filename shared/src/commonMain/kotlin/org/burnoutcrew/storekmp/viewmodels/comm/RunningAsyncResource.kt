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

package org.burnoutcrew.storekmp.viewmodels.comm

import com.dropbox.kmp.external.store4.StoreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningReduce

fun <T> Flow<StoreResponse<T>>.runningAsyncResource() =
    this.map {
        when (it) {
            is StoreResponse.Data -> AsyncResource(it.value, ResourceState.Success)
            is StoreResponse.NoNewData -> AsyncResource(null, ResourceState.Success)
            is StoreResponse.Loading -> AsyncResource(null, ResourceState.Loading)
            is StoreResponse.Error -> AsyncResource(null, ResourceState.Error(it.errorMessageOrNull() ?: "unknown error"))
        }
    }
        .runningReduce { accumulator, value ->
            value.takeIf { it.value != null } ?: accumulator.copy(state = value.state)
        }
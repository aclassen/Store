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

package org.burnoutcrew.storekmp.data.remote

import kotlinx.serialization.Serializable

@Serializable
internal data class RedditApiData(
    val data: RedditApiContent,
    val kind: String
)

@Serializable
internal data class RedditApiChildren(
    val data: RedditApiPost
)

@Serializable
internal data class RedditApiContent(
    val children: List<RedditApiChildren>
)

@Serializable
internal data class RedditApiPost(
    val id: String,
    val title: String,
    val selftext: String? = null,
    val url: String,
)
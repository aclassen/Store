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

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.*
internal class DataFetcher {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    @Throws(Exception::class)
    suspend fun fetchLatestSubreddit(subreditName: String): RedditApiData {
        return withContext(Dispatchers.Default) {
            client.request("$BASE_URL/r/$subreditName/new$JSON_ENCODING") {
                method = HttpMethod.Get
            }
        }.body()
    }

    @Throws(Exception::class)
    suspend fun fetchBestSubreddits(): RedditApiData {
        return withContext(Dispatchers.Default) {
            client.request("$BASE_URL/best$JSON_ENCODING") {
                method = HttpMethod.Get
            }
        }.body()
    }

    companion object {
        private const val BASE_URL: String = "https://www.reddit.com"
        private const val JSON_ENCODING: String = "/.json?raw_json=1"
    }
}
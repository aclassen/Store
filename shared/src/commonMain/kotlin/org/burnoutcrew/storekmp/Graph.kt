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

import com.dropbox.kmp.external.fs3.create
import com.dropbox.kmp.external.fs3.filesystem.FileSystemFactory
import org.burnoutcrew.storekmp.repositories.RedditRepository
import org.burnoutcrew.storekmp.data.local.RedditDao
import org.burnoutcrew.storekmp.data.remote.DataFetcher
import org.burnoutcrew.storekmp.platform.DriverFactory
import org.burnoutcrew.storekmp.sqldelight.data.Database
import org.burnoutcrew.storekmp.viewmodels.BestSubredditsViewModel
import org.burnoutcrew.storekmp.viewmodels.SearchSubredditViewModel


class Graph private constructor(driverFactory: DriverFactory, storeFileSystemRoot: String) {
    private val database = Database(driverFactory.createDriver())
    private val redditDao = RedditDao(database)
    private val fetcher = DataFetcher()
    private val fileSystemFactory = FileSystemFactory.create(storeFileSystemRoot)
    private val repository = RedditRepository(fetcher, redditDao, fileSystemFactory)
    fun provideSearchSubredditViewModel() = SearchSubredditViewModel(repository)
    fun provideBestSubredditsViewModel() = BestSubredditsViewModel(repository)

    companion object {
        @Throws(Exception::class)
        fun create(driverFactory: DriverFactory, storeFileSystemRoot: String) =
            Graph(driverFactory, storeFileSystemRoot)
    }
}
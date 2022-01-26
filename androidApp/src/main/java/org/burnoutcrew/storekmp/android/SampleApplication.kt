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

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.burnoutcrew.storekmp.viewmodels.BestSubredditsViewModel
import org.burnoutcrew.storekmp.platform.DriverFactory
import org.burnoutcrew.storekmp.Graph
import org.burnoutcrew.storekmp.viewmodels.SearchSubredditViewModel

class SampleApplication : Application() {
    private var graph: Graph? = null

    val viewModelProviderFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == SearchSubredditViewModel::class.java) return graph?.provideSearchSubredditViewModel() as T
            if (modelClass == BestSubredditsViewModel::class.java) return graph?.provideBestSubredditsViewModel() as T
            throw IllegalArgumentException()
        }
    }

    override fun onCreate() {
        super.onCreate()
        graph = Graph.create(DriverFactory(this), "${applicationContext.cacheDir}/reddit")
    }

}
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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import org.burnoutcrew.storekmp.viewmodels.BestSubredditsViewModel
import org.burnoutcrew.storekmp.viewmodels.comm.AsyncResource
import org.burnoutcrew.storekmp.viewmodels.comm.ResourceState


@Composable
fun BestSubredditsScreen(viewModel: BestSubredditsViewModel) {
    val scaffoldState = rememberScaffoldState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val postsLifecycleAware = remember(viewModel.feed, lifecycleOwner) {
        viewModel.feed.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val posts by postsLifecycleAware.collectAsState(AsyncResource(null, ResourceState.Success))
    Scaffold(
        scaffoldState = scaffoldState
    )
    {
        PostsList(
            modifier = Modifier.fillMaxSize(),
            posts = posts.value ?: emptyList(),
            posts.state == ResourceState.Loading,
            viewModel::refresh
        )
    }
    ErrorSnackbar(state = posts.state, action = viewModel::refresh, snackbarHostState = scaffoldState.snackbarHostState)
}
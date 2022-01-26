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

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import org.burnoutcrew.storekmp.viewmodels.comm.ResourceState

@Composable
fun ErrorSnackbar(state: ResourceState, action: () -> Unit, snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    (state as? ResourceState.Error)?.run {
        if (snackbarHostState.currentSnackbarData == null) {
            scope.launch {
                val result = snackbarHostState.showSnackbar(this@run.message, "Retry", SnackbarDuration.Indefinite)
                if (result == SnackbarResult.ActionPerformed) {
                    action()
                }
            }
        }
    } ?: snackbarHostState.currentSnackbarData?.dismiss()
}

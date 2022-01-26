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

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.burnoutcrew.storekmp.android.ui.theme.MyApplicationTheme
import kotlin.time.ExperimentalTime

@Composable
fun Root(factory: ViewModelProvider.Factory) {
    MyApplicationTheme {
        val navController = rememberNavController()
        val navigationItems = listOf(
            NavigationItem.Database,
            NavigationItem.FileSystem,
        )
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navigationItems, navController)
            }
        ) {
            NavHost(
                navController,
                startDestination = NavigationItem.Database.route,
                modifier = Modifier.padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
            ) {
                composable(NavigationItem.Database.route) {
                    SearchSubredditScreen(viewModel(factory = factory))
                }
                composable(NavigationItem.FileSystem.route) {
                    BestSubredditsScreen(viewModel(factory = factory))
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(items: List<NavigationItem>, navController: NavController) {
    BottomNavigation(contentColor = MaterialTheme.colors.background) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

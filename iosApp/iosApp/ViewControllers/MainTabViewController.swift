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

import Foundation
import shared
import UIKit

class MainTabBarController: UITabBarController {

    override func loadView() {
        super.loadView()
        let searchSubredditViewController = SearchSubredditViewController()
        searchSubredditViewController.tabBarItem = UITabBarItem(title: "Database",
                                                                image: UIImage(systemName: "server.rack"),
                                                                selectedImage: nil)
        let bestSubredditsViewController = BestSubredditsViewController()
        bestSubredditsViewController.tabBarItem = UITabBarItem(title: "Filesystem",
                                                               image:  UIImage(systemName: "internaldrive"),
                                                               selectedImage: nil)
        viewControllers = [searchSubredditViewController, bestSubredditsViewController]
    }
}

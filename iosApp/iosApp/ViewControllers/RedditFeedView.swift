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

import UIKit
import shared

class RedditFeedView: UIView {

    private let onSearch: (String) -> ()

    init(onRefresh:@escaping ()->() , query: String, onSearch: @escaping (String) -> ()) {
        self.onSearch = onSearch
        collectionView = PostsCollectionView(onRefresh: onRefresh)
        super.init(frame: .zero)
        backgroundColor = .systemBackground
        searchBar.delegate = self
        searchBar.autocapitalizationType = .none
        searchBar.text = query
        createLayout()
    }

    func update(resource: AsyncResource<Posts>) {
        collectionView.update(resource: resource)
    }

    private func createLayout() {
        searchBar.translatesAutoresizingMaskIntoConstraints = false
        collectionView.translatesAutoresizingMaskIntoConstraints = false

        addSubview(searchBar)
        addSubview(collectionView)

        NSLayoutConstraint.activate([
            searchBar.leftAnchor.constraint(equalTo: leftAnchor),
            searchBar.topAnchor.constraint(equalTo: layoutMarginsGuide.topAnchor),
            searchBar.rightAnchor.constraint(equalTo: rightAnchor),
            collectionView.leftAnchor.constraint(equalTo: leftAnchor),
            collectionView.topAnchor.constraint(equalTo: searchBar.bottomAnchor),
            collectionView.rightAnchor.constraint(equalTo: rightAnchor),
            collectionView.bottomAnchor.constraint(equalTo: layoutMarginsGuide.bottomAnchor),
            ])
    }

    private let searchBar = UISearchBar()
    private let collectionView :PostsCollectionView
    
    open override class var requiresConstraintBasedLayout: Bool {
        true
    }

    required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

extension RedditFeedView: UISearchBarDelegate {
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        onSearch(searchBar.text ?? "")
    }
}

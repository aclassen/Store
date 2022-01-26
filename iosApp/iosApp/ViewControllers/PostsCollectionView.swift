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
import UIKit
import shared

class PostsCollectionView: UIView {
    private var collectionViewDataSource: UICollectionViewDiffableDataSource<String, Post>!

    private let collectionView = UICollectionView(frame: .zero,
        collectionViewLayout: UICollectionViewCompositionalLayout.list(using: UICollectionLayoutListConfiguration(appearance: .plain))
    )

    init(onRefresh: @escaping () -> ()) {
        snackBar = Snackbar(onRefresh: onRefresh)
        super.init(frame: .zero)
        backgroundColor = .systemBackground
        progressView.isHidden = true
        collectionViewDataSource = createDataSource()
        collectionView.dataSource = collectionViewDataSource
        collectionView.allowsSelection = false
        collectionView.refreshControl = UIRefreshControl()
        collectionView.refreshControl?.addAction(UIAction(title: "Refresh") { _ in onRefresh() }, for: .valueChanged)
        createLayout()
    }


    func update(resource: AsyncResource<Posts>) {
        if let posts = resource.value?.data {
            var snapshot = NSDiffableDataSourceSnapshot<String, Post>()
            snapshot.appendSections([""])
            snapshot.appendItems(posts, toSection: "")
            collectionViewDataSource.apply(snapshot)
        }

        if (resource.state is ResourceState.Loading && progressView.isHidden) {
            progressView.isHidden = false
            progressView.startAnimating()
        } else {
            if (!progressView.isHidden) {
                progressView.stopAnimating()
                progressView.isHidden = true
            }
            collectionView.refreshControl?.endRefreshing()
        }

        if let state = resource.state as? ResourceState.Error {
            snackBar.setErrorText(state.message)
            snackBar.isHidden = false
        } else {
            snackBar.isHidden = true
        }
    }

    private func createLayout() {
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        progressView.translatesAutoresizingMaskIntoConstraints = false
        snackBar.translatesAutoresizingMaskIntoConstraints = false

        addSubview(collectionView)
        addSubview(progressView)
        addSubview(snackBar)

        NSLayoutConstraint.activate([
            collectionView.leftAnchor.constraint(equalTo: leftAnchor),
            collectionView.topAnchor.constraint(equalTo: layoutMarginsGuide.topAnchor),
            collectionView.rightAnchor.constraint(equalTo: rightAnchor),
            collectionView.bottomAnchor.constraint(equalTo: layoutMarginsGuide.bottomAnchor),
            progressView.topAnchor.constraint(equalTo: collectionView.topAnchor, constant: 20),
            progressView.centerXAnchor.constraint(equalTo: layoutMarginsGuide.centerXAnchor),
            snackBar.leftAnchor.constraint(equalTo: leftAnchor),
            snackBar.rightAnchor.constraint(equalTo: rightAnchor),
            snackBar.bottomAnchor.constraint(equalTo: layoutMarginsGuide.bottomAnchor),
            ])
    }

    private let progressView = UIActivityIndicatorView()
    private let refreshControl = UIRefreshControl()
    private let snackBar: Snackbar

    private func createDataSource() -> UICollectionViewDiffableDataSource<String, Post> {

        let normalCell = UICollectionView.CellRegistration<UICollectionViewListCell, Post> { (cell, indexPath, post) in
            var content = cell.defaultContentConfiguration()
            content.text = post.title
            content.secondaryText = post.text?.isEmpty == false ? post.text : post.url
            cell.contentConfiguration = content
        }
        return UICollectionViewDiffableDataSource<String, Post>(collectionView: collectionView) {
            (collectionView, indexPath, post) -> UICollectionViewCell? in
            return collectionView.dequeueConfiguredReusableCell(using: normalCell, for: indexPath, item: post)
        }
    }

    open override class var requiresConstraintBasedLayout: Bool {
        true
    }

    required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}


private class Snackbar: UIStackView {

    func setErrorText(_ text: String) {
        label.text = text
    }

    init(onRefresh: @escaping () -> ()) {
        super.init(frame: .zero)
        isHidden = true
        label.numberOfLines = 0
        label.font = .systemFont(ofSize: 12)
        label.textColor = .white
        let btn = UIButton(primaryAction: UIAction(title: "Refresh") { _ in onRefresh() })
        btn.setContentHuggingPriority(.required, for: .horizontal)
        btn.setContentCompressionResistancePriority(.required, for: .horizontal)
        backgroundColor = .darkGray
        axis = .horizontal
        distribution = .fill
        directionalLayoutMargins = NSDirectionalEdgeInsets(top: 8, leading: 8, bottom: 8, trailing: 8)
        isLayoutMarginsRelativeArrangement = true
        spacing = 4
        translatesAutoresizingMaskIntoConstraints = false
        addArrangedSubview(label)
        addArrangedSubview(btn)
    }

    private let label = UILabel()

    required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

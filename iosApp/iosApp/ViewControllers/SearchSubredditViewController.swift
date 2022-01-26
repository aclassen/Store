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
import Combine

class SearchSubredditViewController: UIViewController {
    private var cancellable: Set<AnyCancellable>? = nil
    private let viewModel: SearchSubredditViewModelios = ViewModelProvider.shared.provideSearchSubredditViewModel()

    override func loadView() {
        view = RedditFeedView(onRefresh: { [weak self] in self?.viewModel.refresh() },
            query: viewModel.query ,
            onSearch: { [weak self] in self?.viewModel.query = $0 })
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if (cancellable == nil) {
            cancellable = Set()
            createPublisher(wrappedFlow: viewModel.feed)
                .sink(
                receiveCompletion: {
                    if case .failure(let err) = $0 {
                        print(err.errorDescription ?? "")
                    }
                },
                receiveValue: { [weak self] in
                    (self?.view as? RedditFeedView)?.update(resource: $0)
                })
                .store(in: &cancellable!)
        }
    }

    override func viewDidDisappear(_ animated: Bool) {
        cancellable = nil
    }

    deinit {
        viewModel.onCleared()
    }

}

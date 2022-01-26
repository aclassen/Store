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

import Combine
import shared

func createPublisher<T: AnyObject>(wrappedFlow: WrappedFlow<T>) -> AnyPublisher<T, KotlinError> {
    Signal<T, KotlinError> { subscriber in
        let job = wrappedFlow.subscribe { (item) in
            let _ = subscriber.receive(item)
        } onComplete: {
            subscriber.receive(completion: .finished)
        } onThrow: { (error) in
            subscriber.receive(completion: .failure(KotlinError(error)))
        }
        return Combine.AnyCancellable {
            job.cancel(cause: nil)
        }
    }
            .eraseToAnyPublisher()
}

private struct Signal<Output, Failure: Error>: Combine.Publisher {

    private class Subscription: Combine.Subscription {

        let producer: (AnySubscriber<Output, Failure>) -> Combine.AnyCancellable
        let subscriber: AnySubscriber<Output, Failure>

        var cancellable: Combine.AnyCancellable?

        init(producer: @escaping (AnySubscriber<Output, Failure>) -> Combine.AnyCancellable, subscriber: AnySubscriber<Output, Failure>) {
            self.producer = producer
            self.subscriber = subscriber
        }

        func request(_ demand: Combine.Subscribers.Demand) {
            cancellable = producer(subscriber)
        }

        func cancel() {
            cancellable?.cancel()
        }
    }

    private let producer: (AnySubscriber<Output, Failure>) -> Combine.AnyCancellable

    public init(_ producer: @escaping (AnySubscriber<Output, Failure>) -> Combine.AnyCancellable) {
        self.producer = producer
    }

    public func receive<S>(subscriber: S) where S: Combine.Subscriber, Failure == S.Failure, Output == S.Input {
        let subscription = Subscription(producer: producer, subscriber: AnySubscriber(subscriber))
        subscriber.receive(subscription: subscription)
    }
}

class KotlinError: LocalizedError {
    let throwable: KotlinThrowable
    init(_ throwable: KotlinThrowable) {
        self.throwable = throwable
    }
    var errorDescription: String? {
        throwable.message
    }
}

package com.estudio.serverrest.client;

import com.estudio.miprotobuf.BookObject;
import io.grpc.stub.StreamObserver;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Mono;

public class StreamObserverPublisherBookObject extends Mono<BookObject> implements StreamObserver<BookObject> {

    private Subscriber<? super BookObject> subscriber;

    @Override
    public void onNext(BookObject bookObject) {
        subscriber.onNext(bookObject);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onCompleted() {
        subscriber.onComplete();
    }

    @Override
    public void subscribe(CoreSubscriber<? super BookObject> actual) {
        this.subscriber = actual;
        this.subscriber.onSubscribe(new BaseSubscriber() {});
    }
}

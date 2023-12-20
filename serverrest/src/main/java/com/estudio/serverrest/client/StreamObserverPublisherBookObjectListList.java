package com.estudio.serverrest.client;

import com.estudio.miprotobuf.BookObjectList;
import io.grpc.stub.StreamObserver;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Mono;

public class StreamObserverPublisherBookObjectListList extends Mono<BookObjectList> implements StreamObserver<BookObjectList> {

    private Subscriber<? super BookObjectList> subscriber;

    @Override
    public void onNext(BookObjectList bookObjectList) {
        subscriber.onNext(bookObjectList);
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
    public void subscribe(CoreSubscriber<? super BookObjectList> actual) {
        this.subscriber = actual;
        this.subscriber.onSubscribe(new BaseSubscriber() {});
    }
}

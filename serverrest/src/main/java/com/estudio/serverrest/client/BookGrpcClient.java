package com.estudio.serverrest.client;

import com.estudio.miprotobuf.BookObject;
import com.estudio.miprotobuf.Empty;
import com.estudio.miprotobuf.booksServiceGrpcGrpc;
import com.estudio.serverrest.controller.Book;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BookGrpcClient {

    public Mono<Book> addBook(Book book) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 4444).usePlaintext().build();
        //ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("dns:///localhost:4444").usePlaintext().build();

        BookObject request = BookObject.newBuilder()
                .setName(book.getName())
                .setAuthor(book.getAuthor())
                .build();

        StreamObserverPublisherBookObject streamObserverPublisherBookObject = new StreamObserverPublisherBookObject();

        booksServiceGrpcGrpc.booksServiceGrpcStub booksServiceGrpcStub = booksServiceGrpcGrpc.newStub(managedChannel);
        booksServiceGrpcStub.addBookObject(request, streamObserverPublisherBookObject);
        managedChannel.shutdown();

        return streamObserverPublisherBookObject
                .map(bookObject -> {
                    Book bookResponse = new Book();
                    bookResponse.setId(bookObject.getId());
                    bookResponse.setName(bookObject.getName());
                    bookResponse.setAuthor(bookObject.getAuthor());
                    return bookResponse;
                });

    }

    public Flux<Book> getBooks() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 4444).usePlaintext().build();
        //ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("dns:///localhost:4444").usePlaintext().build();

        Empty empty = Empty.newBuilder().build();

        StreamObserverPublisherBookObjectListList streamObserverPublisherBookObjectListList = new StreamObserverPublisherBookObjectListList();

        booksServiceGrpcGrpc.booksServiceGrpcStub booksServiceGrpcStub = booksServiceGrpcGrpc.newStub(managedChannel);
        booksServiceGrpcStub.getAllBookObjects(empty, streamObserverPublisherBookObjectListList);
        managedChannel.shutdown();

        return streamObserverPublisherBookObjectListList
                .flatMapMany(bookObjectList -> {
                    List<BookObject> bookObjects = bookObjectList.getBookObjectsList();
                    return Flux.fromIterable(bookObjects);
                })
                .map(bookObject -> {
                    Book book = new Book();
                    book.setId(bookObject.getId());
                    book.setName(bookObject.getName());
                    book.setAuthor(bookObject.getAuthor());
                    return book;
                });
    }
}

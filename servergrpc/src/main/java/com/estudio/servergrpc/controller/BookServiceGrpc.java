package com.estudio.servergrpc.controller;

import com.estudio.miprotobuf.BookObject;
import com.estudio.miprotobuf.BookObjectList;
import com.estudio.miprotobuf.Empty;
import com.estudio.miprotobuf.booksServiceGrpcGrpc;
import com.estudio.servergrpc.model.Book;
import com.estudio.servergrpc.service.BookService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookServiceGrpc extends booksServiceGrpcGrpc.booksServiceGrpcImplBase {

    @Autowired
    private BookService bookService;

    @Override
    public void getAllBookObjects(Empty request, StreamObserver<BookObjectList> responseObserver) {

        this.bookService.getBooks()
                .map(book -> BookObject.newBuilder()
                        .setId(book.getId())
                        .setName(book.getName())
                        .setAuthor(book.getAuthor())
                        .build())
                .collectList()
                .map(bookObjects -> BookObjectList.newBuilder()
                        .addAllBookObjects(bookObjects)
                        .build())
                .subscribe(responseObserver::onNext);

        responseObserver.onCompleted();
    }

    @Override
    public void addBookObject(BookObject request, StreamObserver<BookObject> responseObserver) {
        Book book = new Book();
        book.setName(request.getName());
        book.setAuthor(request.getAuthor());

        this.bookService.addBook(book)
                .map(bookMono -> BookObject.newBuilder()
                        .setId(bookMono.getId())
                        .setName(bookMono.getName())
                        .setAuthor(bookMono.getAuthor())
                        .build())
                .subscribe(responseObserver::onNext);

        responseObserver.onCompleted();
    }
}

package com.estudio.serverrest.controller;

import com.estudio.serverrest.client.BookGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("book")
public class BookController {
    
    @Autowired
    BookGrpcClient bookGrpcClient;
    
    @PostMapping
    public Mono<Book> addBook(@RequestBody Book book) {
        return this.bookGrpcClient.addBook(book);
    }

    @GetMapping
    public Flux<Book> getBooks() {
        return this.bookGrpcClient.getBooks();
    }
}

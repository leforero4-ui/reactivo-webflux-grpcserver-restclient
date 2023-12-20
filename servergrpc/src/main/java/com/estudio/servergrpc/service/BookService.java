package com.estudio.servergrpc.service;

import com.estudio.servergrpc.model.Book;
import com.estudio.servergrpc.model.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public Flux<Book> getBooks() {
        return bookRepository.findAll(); 
    }
    public Mono<Book> addBook(Book book) {
        return this.bookRepository.save(book);
    }
}

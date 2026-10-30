package com.library.library.service;

import com.library.library.entity.Book;
import com.library.library.exception.NotFoundException;
import com.library.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Книга не найдена"));
    }

    public Book create(Book book) {
        book.setAvailableCount(book.getTotalCount());
        return bookRepository.save(book);
    }

    public Book update(Long id, Book data) {
        Book book = getById(id);
        int issued = book.getTotalCount() - book.getAvailableCount();
        book.setTitle(data.getTitle());
        book.setAuthor(data.getAuthor());
        book.setYear(data.getYear());
        book.setTotalCount(data.getTotalCount());
        book.setAvailableCount(data.getTotalCount() - issued);
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        Book book = getById(id);
        bookRepository.delete(book);
    }
}
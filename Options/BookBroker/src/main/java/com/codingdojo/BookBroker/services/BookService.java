package com.codingdojo.BookBroker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.BookBroker.models.Book;
import com.codingdojo.BookBroker.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repo;
	
	public Book findById(Long id) {

		Optional<Book> result = repo.findById(id);
		if(result.isPresent()) {
			return result.get();
		}
		
		return null;
	}
	

	public List<Book> all() {
		return repo.findAll();
	}
	
	public Book update(Book book) {
		return repo.save(book);
	}
	
	public Book create(Book book) {
		return repo.save(book);
	}
	
	public void delete(Book book) {
		repo.delete(book);
	}
}

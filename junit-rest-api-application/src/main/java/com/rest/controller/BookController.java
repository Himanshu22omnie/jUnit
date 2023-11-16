package com.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.dao.BookRepository;
import com.rest.entity.Book;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@GetMapping
	public List<Book> getallBooks(){
		return bookRepository.findAll();	
	}
	
	@GetMapping(value = "{bookId}")
	public Book getBookById(@PathVariable("bookId") Long bookid){
		return bookRepository.findById(bookid).get();	
	}
	
	@PostMapping("/saveBook")
	public Book createBooksRecord(@RequestBody Book bookRecord) {
		return bookRepository.save(bookRecord);
	}
	
	@PutMapping
	public Book updateBookRecord(@RequestBody Book bookRecord) throws Exception  {
		if (bookRecord == null || bookRecord.getBookId() == null) {
			throw new Exception("Book Id must not be null");
		}
		Optional<Book> optionalBook = bookRepository.findById(bookRecord.getBookId());
		if (!optionalBook.isPresent()) {
			throw new Exception("Book not found with id: " + bookRecord.getBookId());
		}
		
		Book existingBookRecordBook = optionalBook.get();
		existingBookRecordBook.setName(bookRecord.getName());
		existingBookRecordBook.setSummry(bookRecord.getSummry());
		existingBookRecordBook.setRating(bookRecord.getRating());
		
		return bookRepository.save(existingBookRecordBook);
	}
	
	
	@DeleteMapping("/bookId/{id}")
	public void deleteBookById(@PathVariable("id") Long bookId) throws Exception {
		if (bookRepository.findById(bookId).isPresent()) {
			throw new Exception("bookid "+ bookId + "notPresent");
			
		}
		bookRepository.deleteById(bookId);
	}
	

}

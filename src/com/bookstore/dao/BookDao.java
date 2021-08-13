package com.bookstore.dao;

import java.util.List;

import com.bookstore.entities.Book;

//interface to give the methods that need to be implemented by BookDAOJDBC
public interface BookDao {
	
	//get list of all books available
	public List<Book> getAllBooks();

	//returns a book by looking for the id
	public Book getBookById(int id);

	//adds a book to list
	public Book addBook(Book book);

	//deletes a book from list
	public void deleteBook(int id);

	//update a book with a specific id
	public void updateBook(int id, Book book);

	//return a books by looking for ISBN
	public Book getBookByIsbn(String isbn);
}

package com.bookstore.controller;

import java.util.Scanner;

import com.bookstore.dao.BookDao;
import com.bookstore.dao.BookDaoImplJdbc;

import com.bookstore.entities.Book;

public class Main {
	public static void main(String[] args) {
		BookDao dao = new BookDaoImplJdbc();
		Scanner scanner = new Scanner(System.in);
		System.out.println(
				"Choose option 1. Print all books 2. Print book by ID 3. Add Book 4. Delete Book 5. Update Price 6. Find by isbn");
		int val = scanner.nextInt();
		switch (val) {
		case 1:
			for (Book book : dao.getAllBooks()) {
				System.out.println(book);
			}
			break;
		case 2:
			System.out.println("Get book by Id");
			int id = scanner.nextInt();
			System.out.println(dao.getBookById(id));
			break;
		case 3:
			System.out.println("Enter the details of the book: Isbn, Title, author, Price and date(dd/mm/yyyy)");
			String isbn = scanner.next();
			String title = scanner.next();
			String author = scanner.next();
			double price = scanner.nextDouble();
			String sDate = scanner.next();
			java.sql.Date pubDate = java.sql.Date.valueOf(sDate);
			dao.addBook(new Book(isbn, title, author, price, pubDate));
			System.out.println("Book Added");
			break;
		case 4:
			System.out.println("Enter the id of the book");
			int bookId = scanner.nextInt();
			dao.deleteBook(bookId);
			System.out.println("Book Deleted");
			break;
		case 5:
			System.out.println("Enter the id and the price of be updated");
			id = scanner.nextInt();
			Book book = dao.getBookById(id);
			price = scanner.nextDouble();
			book.setPrice(price);
			dao.updateBook(id, book);
			System.out.println("price updated");
			break;
		case 6:
			System.out.println("Enter the isbn");
			String string = scanner.next();
			Book book2 = dao.getBookByIsbn(string);
			System.out.println(book2);
			break;
		default:
			System.out.println("Wrong choice!");
			break;
		}
		scanner.close();
	}
}

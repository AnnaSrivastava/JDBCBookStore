package com.bookstore.controller;

import java.util.Scanner;

//import DAO, Entities and JDBCImplementation
import com.bookstore.dao.BookDao;
import com.bookstore.dao.BookDaoImplJdbc;
import com.bookstore.entities.Book;

//tester class
public class Main {
	
	//tester method
	public static void main(String[] args) {
		BookDao dao = new BookDaoImplJdbc();
		Scanner scMain = new Scanner(System.in);
		System.out.println(
				"1. Print all books \n2. Print book by ID \n3. Add Book \n4. Delete Book \n5. Update Price \n6. Find by isbn");
		int choice = scMain.nextInt();
		switch (choice) {
		case 1:
			for (Book book : dao.getAllBooks()) {
				System.out.println(book);
			}
			break;
		case 2:
			System.out.println("Get book by Id");
			int id = scMain.nextInt();
			System.out.println(dao.getBookById(id));
			break;
		case 3:
			System.out.println("Enter the details of the book: Isbn, Title, author, Price and date(dd/mm/yyyy)");
			String isbn = scMain.next();
			String title = scMain.next();
			String author = scMain.next();
			double price = scMain.nextDouble();
			String sDate = scMain.next();
			java.sql.Date pubDate = java.sql.Date.valueOf(sDate);
			dao.addBook(new Book(isbn, title, author, price, pubDate));
			System.out.println("Book Added");
			break;
		case 4:
			System.out.println("Enter the id of the book");
			int bookId = scMain.nextInt();
			dao.deleteBook(bookId);
			System.out.println("Book Deleted");
			break;
		case 5:
			System.out.println("Enter the id and the price of be updated");
			id = scMain.nextInt();
			Book book = dao.getBookById(id);
			price = scMain.nextDouble();
			book.setPrice(price);
			dao.updateBook(id, book);
			System.out.println("price updated");
			break;
		case 6:
			System.out.println("Enter the isbn");
			String string = scMain.next();
			Book book2 = dao.getBookByIsbn(string);
			System.out.println(book2);
			break;
		default:
			System.out.println("Wrong choice!");
			break;
		}
		scMain.close();
	}//end-of-main
}//end-of-class

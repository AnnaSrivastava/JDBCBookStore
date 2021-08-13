package com.bookstore.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import connection factory, entities, Exceptions
import com.bookstore.dao.factory.DBConnectionFactory;
import com.bookstore.entities.Book;
import com.bookstore.exception.DataAccessException;
import com.bookstore.exception.ResourceNotFoundException;

//class to implement the methods of BookDAO interface
public class BookDaoImplJdbc implements BookDao {
	private Connection connection = null;

	
	public BookDaoImplJdbc() {
		connection = DBConnectionFactory.getConnection();
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<Book>();
		Book book = null;
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement("select * from books");
			ResultSet rSet;
			rSet = pstmt.executeQuery();
			while (rSet.next()) {
				book = new Book(rSet.getString("isbn"), rSet.getString("title"), rSet.getString("author"),
						rSet.getDouble("price"), rSet.getDate("pubDate"));
				books.add(book);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return books;
	}

	@Override
	public Book getBookById(int id) {
		Book book = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement("select * from books where id=?");
			pstmt.setInt(1, id);

			ResultSet rSet = pstmt.executeQuery();
			if (rSet.next()) {
				book = new Book(rSet.getString("isbn"), rSet.getString("title"), rSet.getString("author"),
						rSet.getDouble("price"), rSet.getDate("pubDate"));
			} else {
				throw new ResourceNotFoundException("ID: " + id + " not found");
			}
		} catch (SQLException e) {
			throw new DataAccessException(e.toString());
		}
		return book;
	}

	@Override
	public Book addBook(Book book) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"insert into books(isbn, title, author, price, pubDate) values(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, book.getIsbn());
			pstmt.setString(2, book.getTitle());
			pstmt.setString(3, book.getAuthor());
			pstmt.setDouble(4, book.getPrice());
			pstmt.setDate(5, new Date(book.getPubDate().getTime()));
			pstmt.executeUpdate();

			ResultSet rSet = pstmt.getGeneratedKeys();
			if (rSet.next()) {
				book.setId(rSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	@Override
	public void deleteBook(int id) {
		try {
			PreparedStatement pstmt = connection.prepareStatement("delete from books where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataAccessException(e.toString());
		}

	}

	@Override

	public void updateBook(int id, Book book) {
		try {
			String UPDATE_BOOK_QUERY = "update books set price=? where id=?";
			PreparedStatement pstmt = connection.prepareStatement(UPDATE_BOOK_QUERY);
			pstmt.setInt(2, id);
			pstmt.setDouble(1, book.getPrice());

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException(ex.toString());
		}

	}

	@Override
	public Book getBookByIsbn(String isbn) {
		Book book = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement("select * from books where isbn=?");
			pstmt.setString(1, isbn);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				book = new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("author"),
						rs.getDouble("price"), rs.getDate("pubDate"));
			} else {
				throw new ResourceNotFoundException("book with isbn :=" + isbn + " is not found");
			}       
		} catch (SQLException e) {
			throw new DataAccessException(e.toString());
		}
		return book;
	}

}

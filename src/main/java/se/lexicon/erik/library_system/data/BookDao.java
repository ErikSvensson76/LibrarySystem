package se.lexicon.erik.library_system.data;

import java.util.List;
import java.util.Optional;

import se.lexicon.erik.library_system.model.Book;

public interface BookDao {
	
	Optional<Book> findById(int bookId);
	List<Book> findByTitle(String title);
	List<Book> findAll();
	List<Book> findByAvailable(boolean available);
	List<Book> findByReserved(boolean reserved);
	Book save(Book book);
	void delete(int bookId);
	void removeAll();
	
}

package se.lexicon.erik.library_system.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import se.lexicon.erik.library_system.model.Book;

public class BookData implements BookDao {
	
	private static final BookDao INSTANCE;
	
	static {
		INSTANCE = new BookData();
	}
	
	private List<Book> books;
	
	private BookData() {
		books = new ArrayList<>();
	}
	
	public static BookDao getInstance() {
		return INSTANCE;
	}

	@Override
	public Optional<Book> findById(int bookId) {
		Optional<Book> empty = Optional.empty();
		for(Book book : books) {
			if(book.getBookId() == bookId) {
				return Optional.of(book);
			}
		}
		return empty;
	}

	@Override
	public List<Book> findByTitle(String title) {
		List<Book> result = new ArrayList<>();
		for(Book book : books) {
			if(book.getTitle().equalsIgnoreCase(title)) {
				result.add(book);
			}
		}
		return result;
	}

	@Override
	public List<Book> findAll() {
		return books;
	}

	@Override
	public List<Book> findByAvailable(boolean available) {
		List<Book> result = new ArrayList<>();
		for(Book book : books) {
			if(book.isAvailable() == available) {
				result.add(book);
			}
		}
		return result;
	}

	@Override
	public List<Book> findByReserved(boolean reserved) {
		List<Book> result = new ArrayList<>();
		for(Book book : books) {
			if(book.isReserved() == reserved) {
				result.add(book);
			}
		}
		return result;
	}

	@Override
	public Book save(Book book) {
		if(!books.contains(book)) {
			books.add(book);
		}
		return book;
	}

	@Override
	public void delete(int bookId) {
		Optional<Book> optional = findById(bookId);
		
		if(optional.isPresent()) {
			books.remove(optional.get());
		}else {
			throw new IllegalArgumentException("Book with id " + bookId + " could not be found");
		}
	}

	@Override
	public void removeAll() {
		books.clear();
	}
}

package se.lexicon.erik.library_system.data.jdbc;

import se.lexicon.erik.library_system.data.BookDao;
import se.lexicon.erik.library_system.model.Book;

public interface BookDaoDb extends BookDao {
	
	Book update(Book book);

}

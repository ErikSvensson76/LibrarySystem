package se.lexicon.erik.library_system.data.jdbc;

import se.lexicon.erik.library_system.data.LibraryUserDao;
import se.lexicon.erik.library_system.model.LibraryUser;

public interface LibraryUserDaoDB extends LibraryUserDao {
	
	LibraryUser update(LibraryUser user);

}

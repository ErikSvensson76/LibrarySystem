package se.lexicon.erik.library_system.data;

import java.util.List;
import java.util.Optional;

import se.lexicon.erik.library_system.model.LibraryUser;

public interface LibraryUserDao {
	
	Optional<LibraryUser> findById(int userId);
	List<LibraryUser> findAll();
	LibraryUser save(LibraryUser user);
	void delete(int userId);
	void removeAll();

}

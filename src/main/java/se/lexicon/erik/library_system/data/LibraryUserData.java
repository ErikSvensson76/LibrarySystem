package se.lexicon.erik.library_system.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import se.lexicon.erik.library_system.model.LibraryUser;

public class LibraryUserData implements LibraryUserDao {
	
	//SINGLETON CODE START
	
	private static final LibraryUserDao INSTANCE;
	
	static {
		INSTANCE = new LibraryUserData();
	}
	
	private LibraryUserData() {
		users = new ArrayList<>();
	}
	
	public static LibraryUserDao getInstance() {
		return INSTANCE;
	}
	
	//SINGLETON CODE END
	
	private List<LibraryUser> users;

	@Override
	public Optional<LibraryUser> findById(int userId) {
		Optional<LibraryUser> empty = Optional.empty();
		
		for(LibraryUser user : users) {
			if(user.getUserId() == userId) {
				return Optional.of(user);
			}
		}
		return empty;
	}

	@Override
	public List<LibraryUser> findAll() {
		return users;
	}

	@Override
	public LibraryUser save(LibraryUser user) {
		if(!users.contains(user)) {
			users.add(user);
		}
		return user;
	}

	@Override
	public void delete(int userId) {
		Optional<LibraryUser> optional = findById(userId);
		
		if(optional.isPresent()) {
			users.remove(optional.get());
		}else {
			throw new IllegalArgumentException("LibraryUser with id " + userId + " could not be found");
		}
	}

	@Override
	public void removeAll() {
		users.clear();
	}

}

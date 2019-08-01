package se.lexicon.erik.library_system.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import se.lexicon.erik.library_system.model.LibraryUser;
import static org.junit.Assert.*;

public class LibraryUserDataTest {
	
	private LibraryUserDao testObject;
	private int userId;
	
	public List<LibraryUser> data(){
		return new ArrayList<>(Arrays.asList(
				new LibraryUser(LocalDate.parse("2018-01-01"), "Test 1"),
				new LibraryUser(LocalDate.parse("2018-01-01"), "Test 2"),
				new LibraryUser(LocalDate.parse("2019-03-25"), "Test 3")
				));
	}
	
	@Before
	public void init() {
		testObject = LibraryUserData.getInstance();
		testObject.removeAll();
		for(LibraryUser user : data()) {
			testObject.save(user);
		}
		
		LibraryUser testUser = new LibraryUser(LocalDate.parse("2019-03-25"), "Test 4");		
		testObject.save(testUser);
		userId = testUser.getUserId();		
	}
	
	@Test
	public void test_findById_return_testUser() {
		assertNotNull(testObject.findById(userId));
	}
	
	@Test
	public void test_findById_invalid_return_empty_optional() {
		Optional<LibraryUser> optional = testObject.findById(-1);
		assertFalse(optional.isPresent());
	}
	
	@Test
	public void test_delete_was_successfully_deleted() {
		testObject.delete(userId);
		
		assertFalse(testObject.findById(userId).isPresent());
	}

}

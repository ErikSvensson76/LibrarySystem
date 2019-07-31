package se.lexicon.erik.library_system.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class LibraryUserTest {
	
	@Test
	public void test_create_LibraryUser_SUCCESS() {		
		//ARRANGE
		int expectedUserId = 1;
		LocalDate expectedRegDate = LocalDate.parse("2019-07-31");
		String expectedName = "Test Testsson";
		
		//ACT
		LibraryUser testUser = new LibraryUser(LocalDate.parse("2019-07-31"), "Test Testsson");
		
		//ASSERT
		assertEquals(expectedUserId, testUser.getUserId());
		assertEquals(expectedRegDate, testUser.getRegDate());
		assertEquals(expectedName, testUser.getName());		
	}

}

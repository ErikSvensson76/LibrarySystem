package se.lexicon.erik.library_system.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class LibraryUserTest {
	
	
	
	@Test
	public void test_create_LibraryUser_SUCCESS() {		
		//ARRANGE
		
		LocalDate expectedRegDate = LocalDate.parse("2019-07-31");
		String expectedName = "Test Testsson";
		
		//ACT
		LibraryUser testUser = new LibraryUser(LocalDate.parse("2019-07-31"), "Test Testsson");
		
		
		//ASSERT
		assertTrue(testUser.getUserId() > 0);
		assertEquals(expectedRegDate, testUser.getRegDate());
		assertEquals(expectedName, testUser.getName());		
	}

}

package se.lexicon.erik.library_system.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class BookTest {
	
	@Test
	public void test_create_book_success() {
		int expectedMaxLoanDays = 30;
		BigDecimal expectedFinePerDay = BigDecimal.valueOf(10);
		String expectedDescription = "Test description";
		
		Book testBook = new Book("OCA", 30, BigDecimal.valueOf(10), "Test description");
		
		assertTrue(testBook.getBookId() > 0);
		assertEquals(expectedMaxLoanDays, testBook.getMaxLoanDays());
		assertEquals(expectedFinePerDay, testBook.getFinePerDay());
		assertEquals(expectedDescription, testBook.getDescription());
		assertTrue(testBook.isAvailable());
		assertFalse(testBook.isReserved());
	}

}

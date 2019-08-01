package se.lexicon.erik.library_system.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.lexicon.erik.library_system.model.Book;
import se.lexicon.erik.library_system.model.LibraryUser;
import se.lexicon.erik.library_system.model.Loan;

import static org.junit.Assert.*;

public class LoanDataTest {
	
	private LoanDao testObject;
	private int bookId;
	private int userId;
	private long loanId;
	
	@Before
	public void init() {
		testObject = LoanData.getInstance();
		testObject.removeAll();
		Book book1 = new Book("Test book 1", 10, BigDecimal.valueOf(10),"Test book 1 description");
		bookId = book1.getBookId();
		Book book2 = new Book("Test book 2", 10, BigDecimal.valueOf(10),"Test book 2 description");
		LibraryUser user1 = new LibraryUser(LocalDate.parse("2019-01-01"), "Test Testsson");
		userId = user1.getUserId();		
		Loan loan1 = new Loan(user1, book1, LocalDate.now().minusDays(11));
		loanId = loan1.getLoanId();
		Loan loan2 = new Loan(user1, book2, LocalDate.now());
		testObject.save(loan1);
		testObject.save(loan2);		
	}
	
	@Test
	public void test_findById_loanId_return_not_null() {
		assertNotNull(testObject.findById(loanId).get());
	}
	
	@Test
	public void test_findById_invalid_return_empty_optional() {
		assertFalse(testObject.findById(-3L).isPresent());		
	}
	
	@Test
	public void test_findByOverdue_true_return_list_of_size_1() {
		int expectedSize = 1;
		
		List<Loan> result = testObject.findByOverdue(true);	
		
		assertEquals(expectedSize, result.size());
		assertTrue(result.stream().allMatch(Loan::isOverDue));
	}
	
	@Test
	public void test_findByUserId_return_list_of_size_2() {
		int expectedSize = 2;
		
		List<Loan> result = testObject.findByUserId(userId);
		
		assertEquals(expectedSize, result.size());		
	}
	
	@Test
	public void test_findByBookId_return_list_of_size_1() {
		int expectedSize = 1;
		
		List<Loan> result = testObject.findByBookId(bookId);
		
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void test_findByLoanDate_LocalDateNow_return_list_of_size_1() {
		int expectedSize = 1;
		
		assertEquals(expectedSize, testObject.findByLoanDate(LocalDate.now()).size());
	}
	
	@Test
	public void test_delete_loanId_success() {
		testObject.delete(loanId);
		
		assertFalse(testObject.findById(loanId).isPresent());
	}

}

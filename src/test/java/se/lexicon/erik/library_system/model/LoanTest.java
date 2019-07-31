package se.lexicon.erik.library_system.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoanTest {
	
	private LibraryUser loanTaker;
	private Book book;
	private Loan testLoan;
	private long loanId;
	
	@Before
	public void init() {
		loanTaker = new LibraryUser(LocalDate.parse("2019-07-31"), "Test Testsson");
		book = new Book("Test Book", 30, BigDecimal.valueOf(10), "Test description");
		testLoan = new Loan(loanTaker, book, LocalDate.parse("2019-07-31"));
		loanId = testLoan.getLoanId();
	}
	
	@Test
	public void test_create_loan_success() {
		assertEquals(loanId, testLoan.getLoanId());
		assertEquals(book, testLoan.getBook());
		assertEquals(LocalDate.parse("2019-07-31"), testLoan.getLoanDate());
		assertEquals(loanTaker, testLoan.getLoanTaker());
		assertFalse(testLoan.getBook().isAvailable());
		assertFalse(testLoan.isTerminated());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_add_unavailable_book_throws_IllegalArgumentException() {
		Book unavailableBook = new Book("OCP", 30, BigDecimal.valueOf(10), "Boring book");
		unavailableBook.setAvailable(false);
		
		new Loan(loanTaker, unavailableBook, LocalDate.now());		
	}
	
	@Test
	public void test_loan_is_not_overdue() {
		assertFalse(testLoan.isOverDue());
	}
	
	@Test
	public void test_loan_is_overdue() {
		Book book = new Book("OCP", 30, BigDecimal.valueOf(10), "Boring book");
		Loan testLoan = new Loan(loanTaker, book, LocalDate.now().minusDays(31));
		
		assertTrue(testLoan.isOverDue());
	}
	
	@Test
	public void test_total_fine_is_50() {
		BigDecimal expectedFine = BigDecimal.valueOf(50);
		Book overDueBook = new Book("OCP", 15, BigDecimal.valueOf(10), "Boring book");	
		
		LocalDate twentyDaysAgo = LocalDate.now().minusDays(20);
		
		Loan testLoan = new Loan(loanTaker, overDueBook, twentyDaysAgo);
		
		assertEquals(expectedFine, testLoan.getFine());		
	}
	
	@Test
	public void test_fine_is_0() {
		BigDecimal expectedFine = BigDecimal.ZERO;
		Book book = new Book("OCP", 15, BigDecimal.valueOf(10), "Boring book");	
		
		LocalDate fifteenDaysAgo = LocalDate.now().minusDays(15);
		
		Loan testLoan = new Loan(loanTaker, book, fifteenDaysAgo);
		
		assertEquals(expectedFine, testLoan.getFine());
	}
	
	@Test
	public void test_extendLoan_return_true() {
		Book book = new Book("OCP", 15, BigDecimal.valueOf(10), "Boring book");
		Loan testLoan = new Loan(loanTaker, book, LocalDate.now().minusDays(15));
		
		assertTrue(testLoan.extendLoan());
	}
	
	@Test
	public void test_extendLoan_return_false() {
		Book overDueBook = new Book("OCP", 15, BigDecimal.valueOf(10), "Boring book");	
		
		LocalDate twentyDaysAgo = LocalDate.now().minusDays(20);
		
		Loan testLoan = new Loan(loanTaker, overDueBook, twentyDaysAgo);
		
		assertFalse(testLoan.extendLoan());
	}
	
	@Test
	public void test_extendLoan_on_reserved_book_returns_false() {
		Book reservedBook = new Book("Harry Potter", 30, BigDecimal.valueOf(20), "Popular book");
		Loan theLoan = new Loan(loanTaker, reservedBook, LocalDate.now().minusDays(15));
		reservedBook.setReserved(true);
		
		assertFalse(theLoan.extendLoan());
	}
	
	@Test
	public void test_return_book_loanIsTerminated_and_bookIsAvailable() {
		testLoan.returnBook();
		
		assertTrue(testLoan.isTerminated());
		assertTrue(book.isAvailable());
	}
}

package se.lexicon.erik.library_system.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

/**
 * 
 * @author Erik Svensson
 *
 */
public class Loan {
	
	private static long loanNumber = 0;
	
	private long loanId;
	private LibraryUser loanTaker;
	private Book book;
	private LocalDate loanDate;
	private boolean terminated;
	
	/**
	 * 
	 * @param loanTaker - LibraryUser that is borrowing a book
	 * @param book - The Book that the LibraryUser is borrowing
	 * @param loanDate - The date the loan was started
	 * @throws IllegalArgumentException when trying to lend a book that is not available 
	 */
	public Loan(LibraryUser loanTaker, Book book, LocalDate loanDate) {
		setLoanTaker(loanTaker);
		setBook(book);
		setLoanDate(loanDate);
		this.loanId = ++loanNumber;
		this.terminated = false;
	}

	public LibraryUser getLoanTaker() {
		return loanTaker;
	}

	public void setLoanTaker(LibraryUser loanTaker) {
		this.loanTaker = loanTaker;
	}

	public Book getBook() {
		return book;
	}

	private void setBook(Book book) {
		
		if(!book.isAvailable()) {
			throw new IllegalArgumentException("Book is not available");
		}
		book.setAvailable(false);
		
		this.book = book;
	}
	
	/**
	 * Register the book as being returned by making the book become available.
	 * Terminates the Loan
	 */
	public void returnBook() {
		this.book.setAvailable(true);
		this.terminated = true;
	}
	
	public boolean isTerminated() {
		return terminated;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}

	public long getLoanId() {
		return loanId;
	}
	
	public boolean isOverDue() {
		LocalDate dueDate = loanDate.plusDays(book.getMaxLoanDays());
		boolean overdue = LocalDate.now().isAfter(dueDate);
		return overdue;
	}
	
	public BigDecimal getFine() {		
		Period period = Period.between(loanDate.plusDays(book.getMaxLoanDays()), LocalDate.now());
		
		int daysOverdue = period.getDays();
		
		BigDecimal fine = BigDecimal.ZERO;
		if(daysOverdue > 0) {
			fine = BigDecimal.valueOf(daysOverdue * book.getFinePerDay().longValue());
		}
		return fine;
	}
	
	/**
	 * Prolongs the loan if book is neither reserved or overdue
	 * @return TRUE if loan is extended, otherwise FALSE
	 */
	public boolean extendLoan() {
		if(book.isReserved() || isOverDue()) {
			return false;
		}		
		
		setLoanDate(LocalDate.now());
		return true;		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loanDate == null) ? 0 : loanDate.hashCode());
		result = prime * result + (int) (loanId ^ (loanId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loan other = (Loan) obj;
		if (loanDate == null) {
			if (other.loanDate != null)
				return false;
		} else if (!loanDate.equals(other.loanDate))
			return false;
		if (loanId != other.loanId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Loan [loanId=");
		builder.append(loanId);
		builder.append(", loanTaker=");
		builder.append(loanTaker);
		builder.append(", book=");
		builder.append(book);
		builder.append(", loanDate=");
		builder.append(loanDate);
		builder.append("]");
		return builder.toString();
	}


}

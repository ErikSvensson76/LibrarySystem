package se.lexicon.erik.library_system.model;

import java.math.BigDecimal;

public class Book {
	private static int bookCounter = 0;
	
	private int bookId;
	private String title;
	private boolean available;
	private boolean reserved;
	private int maxLoanDays;
	private BigDecimal finePerDay;
	private String description;
	
	public Book(String title, int maxLoanDays, BigDecimal finePerDay, String description) {
		setTitle(title);
		setMaxLoanDays(maxLoanDays);
		setFinePerDay(finePerDay);
		setDescription(description);
		this.bookId = ++bookCounter;
		setAvailable(true);
		setReserved(false);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public int getMaxLoanDays() {
		return maxLoanDays;
	}

	public void setMaxLoanDays(int maxLoanDays) {
		this.maxLoanDays = maxLoanDays;
	}

	public BigDecimal getFinePerDay() {
		return finePerDay;
	}

	public void setFinePerDay(BigDecimal finePerDay) {
		this.finePerDay = finePerDay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBookId() {
		return bookId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bookId;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (bookId != other.bookId)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Book [bookId=");
		builder.append(bookId);
		builder.append(", title=");
		builder.append(title);
		builder.append(", available=");
		builder.append(available);
		builder.append(", reserved=");
		builder.append(reserved);
		builder.append(", maxLoanDays=");
		builder.append(maxLoanDays);
		builder.append(", finePerDay=");
		builder.append(finePerDay);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	

}

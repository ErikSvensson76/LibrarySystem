package se.lexicon.erik.library_system.data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import se.lexicon.erik.library_system.model.Loan;

public interface LoanDao {
	
	Optional<Loan> findById(long loanId);
	List<Loan> findAll();
	List<Loan> findByOverdue(boolean overdue);
	List<Loan> findByUserId(int userId);
	List<Loan> findByBookId(int bookId);
	List<Loan> findByLoanDate(LocalDate loanDate);
	Loan save(Loan loan);
	void delete(long loanId);
	void removeAll();

}

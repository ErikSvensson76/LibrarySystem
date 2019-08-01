package se.lexicon.erik.library_system.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import se.lexicon.erik.library_system.model.Loan;

public class LoanData implements LoanDao{
	
	private static final LoanDao INSTANCE;
	
	static {
		INSTANCE = new LoanData();
	}
	
	private LoanData() {
		loans = new ArrayList<>();
	}
	
	private List<Loan> loans;
	
	public static LoanDao getInstance() {
		return INSTANCE;
	}

	@Override
	public Optional<Loan> findById(long loanId) {
		
		for(Loan loan : loans) {
			if(loan.getLoanId() == loanId) {
				return Optional.of(loan);
			}
		}
		return Optional.empty();
	}

	@Override
	public List<Loan> findAll() {
		return loans;
	}

	@Override
	public List<Loan> findByOverdue(boolean overdue) {
		List<Loan> result = new ArrayList<>();
		for(Loan loan : loans) {
			if(loan.isOverDue() == overdue) {
				result.add(loan);
			}
		}
		return result;
	}

	@Override
	public List<Loan> findByUserId(int userId) {
		List<Loan> result = new ArrayList<>();
		for(Loan loan : loans) {
			if(loan.getLoanTaker().getUserId() == userId) {
				result.add(loan);
			}
		}
		return result;
	}

	@Override
	public List<Loan> findByBookId(int bookId) {
		List<Loan> result = new ArrayList<>();
		for(Loan loan : loans) {
			if(loan.getBook().getBookId() == bookId) {
				result.add(loan);
			}
		}
		return result;
	}

	@Override
	public List<Loan> findByLoanDate(LocalDate loanDate) {
		List<Loan> result = new ArrayList<>();
		for(Loan loan : loans) {
			if(loan.getLoanDate().equals(loanDate)) {
				result.add(loan);
			}
		}
		return result;
	}

	@Override
	public Loan save(Loan loan) {
		if(!loans.contains(loan)) {
			loans.add(loan);
		}
		return loan;
	}

	@Override
	public void delete(long loanId) {
		Optional<Loan> optional = findById(loanId);
		
		if(optional.isPresent()) {
			loans.remove(optional.get());
		}else {
			throw new IllegalArgumentException("Loan with id " + loanId + " could not be found");
		}		
	}

	@Override
	public void removeAll() {
		loans.clear();		
	}
}

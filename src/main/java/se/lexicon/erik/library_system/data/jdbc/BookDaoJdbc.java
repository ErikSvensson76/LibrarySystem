package se.lexicon.erik.library_system.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import se.lexicon.erik.library_system.data.Database;
import se.lexicon.erik.library_system.model.Book;

public class BookDaoJdbc implements BookDaoDb {
	
	private static final BookDaoDb INSTANCE;
	
	static {
		INSTANCE = new BookDaoJdbc();
	}
	
	private BookDaoJdbc() {}
	
	public static BookDaoDb getInstance() {
		return INSTANCE;
	}
	
	private static final String FIND_BY_ID = "SELECT * FROM book WHERE BOOK_ID = ?";
	private static final String FIND_BY_TITLE = "SELECT * FROM book WHERE TITLE = ?";
	private static final String FIND_ALL = "SELECT * FROM book";
	private static final String FIND_BY_AVAILABLE = "SELECT * FROM book WHERE AVAILABLE = ?";
	private static final String FIND_BY_RESERVED = "SELECT * FROM book WHERE RESERVED = ?";
	private static final String INSERT_NEW_BOOK = "INSERT INTO book "
			+ "(TITLE,AVAILABLE,RESERVED,MAX_DAYS,FINE_PER_DAY, DESCRIPTION)"
			+ "VALUES(?,?,?,?,?,?)";
	private static final String DELETE_BY_ID = "DELETE FROM book WHERE BOOK_ID = ?";
	private static final String UPDATE_BOOK = "UPDATE book SET TITLE = ?, AVAILABLE = ?, RESERVED = ?, MAX_DAYS = ?, FINE_PER_DAY = ?, DESCRIPTION = ?"
			+ " WHERE BOOK_ID = ?";

	@Override
	public Optional<Book> findById(int bookId) {
		Book book = null;
		try(Connection conn = Database.getConnection();
			PreparedStatement statement = createFindByIdStatement(conn, bookId, FIND_BY_ID);
			ResultSet rs = statement.executeQuery()){
			
			while(rs.next()) {
				book = convertResultSetToBook(rs);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return book == null ? Optional.empty() : Optional.of(book);
	}

	private PreparedStatement createFindByIdStatement(Connection conn, int bookId, final String findById) throws SQLException {
		PreparedStatement statement = conn.prepareStatement(findById);
		statement.setInt(1, bookId);
		return statement;
	}

	@Override
	public List<Book> findByTitle(String title) {
		List<Book> result = new ArrayList<>();
		
		try(Connection conn = Database.getConnection();
			PreparedStatement statement = createFindByTitleStatement(conn, title, FIND_BY_TITLE);
			ResultSet rs = statement.executeQuery();){
			
			while(rs.next()) {
				result.add(convertResultSetToBook(rs));
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
		
	}

	private PreparedStatement createFindByTitleStatement(Connection conn, String title, final String findByTitle) throws SQLException {
		PreparedStatement statement = conn.prepareStatement(findByTitle);
		statement.setString(1, title);		
		return statement;
	}

	@Override
	public List<Book> findAll() {
		List<Book> result = new ArrayList<>();
		try(Connection conn = Database.getConnection();
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(FIND_ALL);){
				
				while(rs.next()) {
					result.add(convertResultSetToBook(rs));
				}
				
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return result;
	}

	@Override
	public List<Book> findByAvailable(boolean available) {
		List<Book> result = new ArrayList<>();
		
		try(Connection conn = Database.getConnection();
			PreparedStatement statement = createFindByAvailableOrReservedStatement(conn, available, FIND_BY_AVAILABLE);
			ResultSet rs = statement.executeQuery();){
			
			while(rs.next()) {
				result.add(convertResultSetToBook(rs));
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}

	private PreparedStatement createFindByAvailableOrReservedStatement(Connection conn, boolean bool, final String sql) throws SQLException {
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setBoolean(1, bool);
		return statement;
	}

	@Override
	public List<Book> findByReserved(boolean reserved) {
		List<Book> result = new ArrayList<>();
		
		try(Connection conn = Database.getConnection();
			PreparedStatement statement = createFindByAvailableOrReservedStatement(conn, reserved, FIND_BY_RESERVED);
			ResultSet rs = statement.executeQuery();){
			
			while(rs.next()) {
				result.add(convertResultSetToBook(rs));
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Book save(Book book) {
		if(book.getBookId() != 0) {
			return book;
		}
		
		ResultSet keySet = null;		
		Book savedBook = null;
		
		try(Connection conn = Database.getConnection();
			PreparedStatement statement = conn.prepareStatement(INSERT_NEW_BOOK, Statement.RETURN_GENERATED_KEYS)){
			
			statement.setString(1, book.getTitle());
			statement.setBoolean(2, book.isAvailable());
			statement.setBoolean(3, book.isReserved());
			statement.setInt(4, book.getMaxLoanDays());
			statement.setBigDecimal(5, book.getFinePerDay());
			statement.setString(6, book.getDescription());
			
			statement.executeUpdate();
			
			keySet = statement.getGeneratedKeys();
			while(keySet.next()) {
				savedBook = new Book(keySet.getInt(1),
						book.getTitle(),
						book.isAvailable(),
						book.isReserved(),
						book.getMaxLoanDays(),
						book.getFinePerDay(),
						book.getDescription());
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(keySet != null) {
				try {
					keySet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return savedBook;		
	}

	@Override
	public void delete(int bookId) {
		try(Connection conn = Database.getConnection();
			PreparedStatement statement = conn.prepareStatement(DELETE_BY_ID)){
			
			statement.setInt(1, bookId);
			statement.execute();
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public Book update(Book book) {
		if(book.getBookId() == 0) {
			return book;
		}
		
		try(Connection conn = Database.getConnection();
			PreparedStatement statement = conn.prepareStatement(UPDATE_BOOK)){
						
			statement.setString(1, book.getTitle());
			statement.setBoolean(2, book.isAvailable());
			statement.setBoolean(3, book.isReserved());
			statement.setInt(4, book.getMaxLoanDays());
			statement.setBigDecimal(5, book.getFinePerDay());
			statement.setString(6, book.getDescription());
			statement.setInt(7, book.getBookId());
			
			statement.execute();			
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return book;
	}
	
	private Book convertResultSetToBook(ResultSet rs) throws SQLException {
		return new Book(
				rs.getInt(1),
				rs.getString(2),
				rs.getBoolean(3),
				rs.getBoolean(4),
				rs.getInt(5),
				rs.getBigDecimal(6),
				rs.getString(7)
				);
	}

}

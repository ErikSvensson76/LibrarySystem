package se.lexicon.erik.library_system.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import se.lexicon.erik.library_system.data.Database;
import se.lexicon.erik.library_system.model.LibraryUser;

public class LibraryUserDaoJdbc implements LibraryUserDaoDB {
	
	private static final LibraryUserDaoDB INSTANCE;
	
	static {
		INSTANCE = new LibraryUserDaoJdbc();
	}
	
	private LibraryUserDaoJdbc() {}
	
	public static LibraryUserDaoDB getInstance() {
		return INSTANCE;
	}
	
	private static final String INSERT_NEW = "INSERT INTO libraryuser (REG_DATE,USER_NAME)VALUES(?,?)";
	private static final String FIND_BY_ID = "SELECT * FROM libraryuser WHERE USER_ID = ?";
	private static final String FIND_ALL = "SELECT * FROM libraryuser";
	private static final String DELETE_USER = "DELETE FROM libraryuser WHERE USER_ID = ?";
	private static final String UPDATE_USER = "UPDATE libraryuser SET USER_NAME = ? WHERE USER_ID = ?";

	@Override
	public Optional<LibraryUser> findById(int userId) {
		LibraryUser user = null;
		
		try(Connection conn = Database.getConnection();
			PreparedStatement statement = createFindByIdStatement(conn, userId);
			ResultSet rs = statement.executeQuery()){
			
			while(rs.next()) {
				user = createLibraryUserFromResultSet(rs);				 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user == null? Optional.empty() : Optional.of(user);
		
	}

	private LibraryUser createLibraryUserFromResultSet(ResultSet rs) throws SQLException {
		
		return new LibraryUser(rs.getInt(1), rs.getObject(2, LocalDate.class) , rs.getString(3));
	}

	private PreparedStatement createFindByIdStatement(Connection conn, int userId) throws SQLException {
		PreparedStatement statement = conn.prepareStatement(FIND_BY_ID);
		statement.setInt(1, userId);
		return statement;
	}

	@Override
	public List<LibraryUser> findAll() {
		
		List<LibraryUser> result = new ArrayList<>();
		try(Connection conn = Database.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(FIND_ALL)){
			
			while(rs.next()) {
				result.add(createLibraryUserFromResultSet(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}

	@Override
	public LibraryUser save(LibraryUser user) {
		int userId = 0;
		LibraryUser saved = null;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try{
			conn = Database.getConnection();
			statement = conn.prepareStatement(INSERT_NEW, Statement.RETURN_GENERATED_KEYS);
			
			
			
			statement.setObject(1, user.getRegDate());
			statement.setString(2, user.getName());
			statement.executeUpdate();
			
			rs = statement.getGeneratedKeys();
			while(rs.next()) {
				userId = rs.getInt(1);
			}			
			saved = new LibraryUser(userId, user.getRegDate(), user.getName());		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn != null) {
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
				if(rs != null) {
					rs.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}			
		}
		return saved;
	}

	

	@Override
	public void delete(int userId) {
		try(Connection conn = Database.getConnection();
			PreparedStatement statement = conn.prepareStatement(DELETE_USER)){
			
			statement.setInt(1, userId);
			statement.execute();
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
	}

	@Override
	public LibraryUser update(LibraryUser user) {
		Connection conn = null;
		PreparedStatement statement = null;		
		
		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(UPDATE_USER);
			statement.setString(1, user.getName());
			statement.setInt(2, user.getUserId());
			
			statement.executeUpdate();
			conn.commit();
		}catch(SQLException ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}finally {
			try {
				if(conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
				if(statement != null) {
					statement.close();
				}
			}catch(SQLException ex) {
				ex.printStackTrace();
			}			
		}
		return user;
	}

}

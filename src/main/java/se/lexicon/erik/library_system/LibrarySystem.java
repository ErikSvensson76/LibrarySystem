package se.lexicon.erik.library_system;

import java.sql.SQLException;

import se.lexicon.erik.library_system.data.jdbc.LibraryUserDaoDB;
import se.lexicon.erik.library_system.data.jdbc.LibraryUserDaoJdbc;

public class LibrarySystem {

	public static void main(String[] args) throws SQLException {
		LibraryUserDaoDB dao = LibraryUserDaoJdbc.getInstance();
		
			
		
	}

}

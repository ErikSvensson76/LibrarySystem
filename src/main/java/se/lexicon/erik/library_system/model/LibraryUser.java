package se.lexicon.erik.library_system.model;

import java.time.LocalDate;

/**
 * Stores basic information about library users
 * @author Erik Svensson
 *
 */
public class LibraryUser {
	private static int userCount = 0;
	private int userId;
	private LocalDate regDate;
	private String name;
	
	public LibraryUser(LocalDate regDate, String name) {
		this.regDate = regDate;
		setName(name);
		this.userId = ++userCount;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public LocalDate getRegDate() {
		return regDate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((regDate == null) ? 0 : regDate.hashCode());
		result = prime * result + userId;
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
		LibraryUser other = (LibraryUser) obj;
		if (regDate == null) {
			if (other.regDate != null)
				return false;
		} else if (!regDate.equals(other.regDate))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LibraryUser [userId=" + userId + ", regDate=" + regDate + ", name=" + name + "]";
	}
}

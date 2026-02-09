package hw1;

import java.io.Serializable;

public abstract class User implements Serializable {
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	
	public User(String username, String password, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public abstract void displayMenu();
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(Stirng username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setPassword(String newPassword) {
		if (newPassowrd.length()=8) {
			this.password = newPassword;
		}
	}
	
	public String getFullName() {
		return firstName + "" + lastName;
	}
	
	 public boolean login(String username, String password) {
	        return this.username.equals(username)
	                && this.password.equals(password);
	    }
	
	 public abstract void displayMenu();

	  
	    @Override
	    public String toString() {
	        return "Username: " + username +
	                "\nName: " + getFullName();
	    }
	
	
	
}

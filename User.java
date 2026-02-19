package hw1;

import java.io.Serializable;

/**
 * Abstract User Class (Parent of Admin and Student)
 * Demonstrates: Inheritance, Encapsulation, Abstraction
 */
public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

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

    // Abstract method - overridden by Admin and Student (Method Overriding)
    public abstract void displayMenu();

    // Getters (Encapsulation)
    public String getUsername()  { return username; }
    public String getPassword()  { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getFullName()  { return firstName + " " + lastName; }

    // Setters
    public void setUsername(String username)   { this.username = username; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }

    // Method Overloading: two setPassword versions
    public void setPassword(String newPassword) {
        if (newPassword != null && newPassword.length() >= 8) {
            this.password = newPassword;
        } else {
            System.out.println("Password must be at least 8 characters.");
        }
    }

    public void setPassword(String oldPassword, String newPassword) {
        if (!this.password.equals(oldPassword)) {
            System.out.println("Old password is incorrect.");
            return;
        }
        setPassword(newPassword);
    }

    // Login validation
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nName: " + getFullName();
    }
}

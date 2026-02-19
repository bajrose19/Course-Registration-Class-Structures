package hw1;

import java.io.Serializable;

/**
 * Abstract User Class (Parent of Admin and Student)
 * Demonstrates Inheritance, Encapsulation, and Abstraction.
 */
public abstract class User implements Serializable {

    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;

    // Constructor
    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Abstract method (will be overridden in Admin and Student)
    public abstract void displayMenu();

    // Getters (Encapsulation)
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // Setters (Encapsulation)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Method Overloading example (different logic for password setting)
    public void setPassword(String newPassword) {
        // Password must be at least 8 characters
        if (newPassword != null && newPassword.length() >= 8) {
            this.password = newPassword;
        } else {
            System.out.println("Password must be at least 8 characters.");
        }
    }

    // Returns full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Login validation method
    public boolean login(String username, String password) {
        return this.username.equals(username) &&
               this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Username: " + username +
               "\nName: " + getFullName();
    }
}

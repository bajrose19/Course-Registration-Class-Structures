package hw1;

import java.util.ArrayList;

public class Student extends User implements StudentInterface { //Student inherits from the user class and implements the StudentInterface

    // used with serialization to ensure that the old changes work with any new changes made by the user especially in terms of the student class 
    private static final long serialVersionUID = 1L;

    // The main course list (array) that the student can access 
    private final ArrayList<Course> allCourses;

    // The list (array) of classes that the student is enrolled in 
    private final ArrayList<Course> registeredCourses;

   // setting up the first name, last name, username, and password to be entered by the user
    // using the super keyword to calls the parent class and the first name, last name, username, and password defined there  
    public Student(String username, String password, String firstName, String lastName,
                   ArrayList<Course> allCourses) { //
        super(username, password, firstName, lastName);
        this.allCourses        = allCourses;
        this.registeredCourses = new ArrayList<>(); //declaring registeredCourses as a new array list 
    }

    @Override
    // shows every course available from the csv file 
    public ArrayList<Course> viewAllCourses() {
        return new ArrayList<>(allCourses);
    }

    @Override
    // returns only courses that are not full and that students can register for
    // if the course object is not full and does exist the student can add it to their registered courses
    public ArrayList<Course> viewAvailableCourses() {
        ArrayList<Course> available = new ArrayList<>();
        for (Course c : allCourses) {
            if (c != null && !c.isFull()) {
                available.add(c);
            }
        }
        return available;
    }

    @Override
    // enrolls a student in a course
    // returns false if course is course is full 
    public boolean courseRegistration(Course c) {
        if (c == null)          
            return false;
        if (isRegisteredIn(c))  
            return false;
        if (c.isFull())         
            return false;

        c.addStudent(this);           // adds name to course roster
        registeredCourses.add(c);     
        return true;                  // returns true 
    }

    @Override
    // removes a student from a course 
    // returns false if student is not registered
    public boolean courseWithdrawal(Course c) {
        if (c == null)         
            return false;
        if (!isRegisteredIn(c)) 
            return false;

        c.removeStudent(getFullName()); // removes name to the course roster
        registeredCourses.remove(c);
        return true;                    // returns true 
    }

    @Override
    // returns a copy of a student's currents course list 
    public ArrayList<Course> viewRegisteredCourses() {
        return new ArrayList<>(registeredCourses);
    }

    @Override
    // checks whether a student is enrolled in a specific course
    // if the course object is empty the student cannot enroll as it returns false
    public boolean isRegisteredIn(Course c) {
        if (c == null) 
            return false;
        return registeredCourses.contains(c);
    }

    @Override
    // prints the student menu
    public void displayMenu() {
        System.out.println(
           
            "\n  Course Registration System — Student" +  
            "\nCourse Management:" +
            "\n  1. View all courses" +
            "\n  2. View available (not full) courses" +
            "\n  3. Register for a course" +
            "\n  4. Withdraw from a course" +
            "\n  5. View my registered courses" +
            "\n  6. Exit" 
        );
    }

    @Override
    public String toString() {
        return super.toString() + "\nRegistered Courses: " + registeredCourses.size();
    }
}

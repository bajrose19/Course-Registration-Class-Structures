package hw1;

import java.util.ArrayList;

/**
 * Student class represents a student user in the Course Registration System.
 * Extends User (Inheritance - Req 07) and implements StudentInterface (Req 06).
 * Demonstrates: Encapsulation, Polymorphism, Inheritance, Method Overriding
 */
public class Student extends User implements StudentInterface {

    private static final long serialVersionUID = 1L;

    // The shared master course list (injected so student can browse all courses)
    private final ArrayList<Course> allCourses;

    // Courses this student is currently enrolled in
    private final ArrayList<Course> registeredCourses;

    public Student(String username, String password, String firstName, String lastName,
                   ArrayList<Course> allCourses) {
        super(username, password, firstName, lastName);
        this.allCourses        = allCourses;
        this.registeredCourses = new ArrayList<>();
    }

    @Override
    // viewAllCourses shows every course available in the system (Req 04-1)
    public ArrayList<Course> viewAllCourses() {
        return new ArrayList<>(allCourses);
    }

    @Override
    // viewAvailableCourses returns only courses that are NOT full (Req 04-2)
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
    // courseRegistration enrolls the student in a course (Req 04-3)
    // returns false if: course is null, already registered, or course is full
    public boolean courseRegistration(Course c) {
        if (c == null)          return false;
        if (isRegisteredIn(c))  return false;
        if (c.isFull())         return false;

        c.addStudent(this);           // adds name to course roster
        registeredCourses.add(c);     // tracks locally
        return true;
    }

    @Override
    // courseWithdrawal removes the student from a course (Req 04-4)
    // returns false if: course is null or student is not registered
    public boolean courseWithdrawal(Course c) {
        if (c == null)         return false;
        if (!isRegisteredIn(c)) return false;

        c.removeStudent(getFullName());
        registeredCourses.remove(c);
        return true;
    }

    @Override
    // viewRegisteredCourses returns a copy of the student's own course list (Req 04-5)
    public ArrayList<Course> viewRegisteredCourses() {
        return new ArrayList<>(registeredCourses);
    }

    @Override
    // isRegisteredIn checks whether this student is enrolled in the given course
    public boolean isRegisteredIn(Course c) {
        if (c == null) return false;
        return registeredCourses.contains(c);
    }

    @Override
    // displayMenu prints the student menu — Method Overriding of User.displayMenu()
    public void displayMenu() {
        System.out.println(
            "\n========================================" +
            "\n  Course Registration System — Student" +
            "\n========================================" +
            "\nCourse Management:" +
            "\n  1. View all courses" +
            "\n  2. View available (not full) courses" +
            "\n  3. Register for a course" +
            "\n  4. Withdraw from a course" +
            "\n  5. View my registered courses" +
            "\n  6. Exit" +
            "\n========================================"
        );
    }

    @Override
    public String toString() {
        return super.toString() + "\nRegistered Courses: " + registeredCourses.size();
    }
}

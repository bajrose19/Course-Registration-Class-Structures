package hw1;

import java.util.ArrayList;

/**
 * StudentInterface defines the actions a Student can perform.
 * Implements: ADT concept (Req 06)
 */
public interface StudentInterface {

    // Course Management (Req 04)
    ArrayList<Course> viewAllCourses();
    ArrayList<Course> viewAvailableCourses();      // not full courses
    boolean courseRegistration(Course c);
    boolean courseWithdrawal(Course c);
    ArrayList<Course> viewRegisteredCourses();

    // Helper
    boolean isRegisteredIn(Course c);
}

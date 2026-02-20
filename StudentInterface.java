package hw1;

import java.util.ArrayList;

public interface StudentInterface { // is used by the student class and contains the abstract methods that are fully defined in the student class

    // course management
    ArrayList<Course> viewAllCourses();
    ArrayList<Course> viewAvailableCourses();      
    boolean courseRegistration(Course c);
    boolean courseWithdrawal(Course c);
    ArrayList<Course> viewRegisteredCourses();
    boolean isRegisteredIn(Course c);
}

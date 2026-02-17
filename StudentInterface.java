package hw1;

import java.util.ArrayList;

public interface StudentInterface {
	// methods the student class will be implementing 
	
	ArrayList<Course> viewRegisteredCourses();
	ArrayList<Course> viewAvaliableCourses(ArrayList<Course> courses);
	boolean courseRegistration(Course courses);
	boolean courseWithdrawl(Course courses);
	ArrayList<Course> viewAllCourses(ArrayList<Course> courses);
	void displayMenu();
	boolean courseWithdrawal(Course courses);
}

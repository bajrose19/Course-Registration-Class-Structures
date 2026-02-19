package hw1;

import java.util.ArrayList;

public interface StudentInterface {
	// methods the student class will be implementing 
	
	ArrayList<Course> viewRegisteredCourses();
	ArrayList<Course> viewAvaliableCourses(ArrayList<Course> courses);
	boolean courseRegistration(String courseId, ArrayList<Course> courses);
	boolean courseWithdrawl(String courseId, ArrayList<Course> courses);
	ArrayList<Course> viewAllCourses(ArrayList<Course> courses);

}


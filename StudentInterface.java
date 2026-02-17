package hw1;

public class StudentInterface {
	// methods the student class will be implementing 
	
	ArrayList<Course> viewRegisteredCourses();
	ArrayList<Course> viewAvaliableCourses(ArrayList<Course> courses);
	boolean courseRegistration(Course courses);
	boolean courseWithdrawl(Course courses);
	ArrayList<Course> viewAllCourses(ArrayList<Course> courses);

}

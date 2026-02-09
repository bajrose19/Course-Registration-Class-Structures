package hw1;

public class CourseRegistrationSystem {

	static ArrayList<Course> courses = new ArrayList<>();
	static ArrayList<Student> students = new ArrayList<>();
	
	public static void main(String[] args) {
		if (!loadSerializedData()) {
			readCoursesFromCSV("MyUniversityCourses.csv")
		}
		
		startSystem();
		
		saveSerializedData();
	}
}

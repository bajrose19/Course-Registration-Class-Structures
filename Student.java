package hw1;

import java.util.ArrayList;


public class Student extends User implements StudentInterface {
	
	private ArrayList<Course> registeredCourses;
	private final ArrayList<Course> courses;
	
	public Student(String firstName, String lastName, String username, String password) {
		super(firstName, lastName, username, password);
		
        this.registeredCourses = new ArrayList<>();
		
		}
	
	public ArrayList<Course> viewRegisteredCourses() {
		ArrayList<Course> myCourses = new ArrayList<>();

        for (Course c : courses) {
            if (c != null && c.getStudentNames().contains(getFullName())) {
                myCourses.add(c);
            }
        }

        return myCourses;
    }
	
	public ArrayList<Course> viewAvaliableCourses(ArrayList<Course> courses) {
		ArrayList<Course> openCourses = new ArrayList<>();

        for (Course c : courses) {
            if (c != null && !c.isFull()) {
                openCourses.add(c);
            }
        }

        return openCourses;
    }
	
	@Override
	public boolean courseRegistration(String courseId, ArrayList<Course> courses) {
		if (courseId == null || courses == null) return false;

        for (Course c : courses) {
            if (c != null && courseId.equals(c.getCourseID())) {

                // Already enrolled
                if (c.getStudentNames().contains(getFullName())) {
                    return false;
                }

                // Course full
                if (c.isFull()) {
                    return false;
                }

                c.addStudent(getFullName());
                return true;
            }
        }
    }
	
	@Override
	public boolean courseWithdrawl(String courseId, ArrayList<Course> courses) {
		if (courseId == null || courses == null) return false;

        for (Course c : courses) {
            if (c != null && courseId.equals(c.getCourseID())) {

                if (!c.getStudentNames().contains(getFullName())) {
                    return false; // Not enrolled
                }

                c.removeStudent(getFullName());
                return true;
            }
        }
        return false;
    }
	
	public ArrayList<Course> viewAllCourses(ArrayList<Course> courses) {
		return new ArrayList<>(courses);
		
	}
	
	 @Override
	    public void displayMenu() {
	        System.out.println(
	            "Welcome to the Course Registration System (Admin).\n" +
	            "Choose an option:\n" +
	            "1. Create a new course\n" +
	            "2. Delete a course\n" +
	            "3. Edit a course\n" +
	            "4. Display a course\n" +
	            "5. Register a student\n" +
	            "6. View all courses\n" +
	            "7. View full courses\n" +
	            "8. Write full courses to a file\n" +
	            "9. View students in a course\n" +
	            "10. View a student's courses\n" +
	            "11. Sort courses by current students\n" +
	            "12. Exit"
	        );
	    }
		
}

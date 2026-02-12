package hw1;

import java.util.ArrayList;

public class Admin implements AdminInterface {
	private ArrayList<Course> courses;
	private ArrayList<Student> students;
	public Admin(ArrayList<Student> students, ArrayList<>) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean deleteCourse(String courseId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editCourse(String courseId, int maxStudents, String instructor, int section, String location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean writeFullCoursesToFile(String filename) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> viewStudentsInCourse(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean createCourse(Course c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Course getCourseById(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean registerStudent(Student s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Course> viewAllCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> viewFullCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> viewCoursesOfStudent(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> sortCoursesByCurrentStudents() {
		// TODO Auto-generated method stub
		return null;
	}

}

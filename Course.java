package hw1;

import java.io.Serializable;
import java.util.ArrayList;

public class Course {
	private String courseName;
	private String courseID;
	private int maxStudents;
	private int currentStudents;
	private ArrayList<String> studentNames;
	private String instructor;
	private int sectionNumber;
	private String location;
	
	public Course(String courseName, String courseID, int maxStudents,
            String instructor, int sectionNumber, String location) {
		this.courseName = courseName;
        this.courseID = courseID;
        this.maxStudents = maxStudents;
        this.currentStudents = 0; // initially zero
        this.studentNames = new ArrayList<>();
        this.instructor = instructor;
        this.sectionNumber = sectionNumber;
        this.location = location;
    
	}

	public String getCourseName() {
        return courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public ArrayList<String> getStudentNames() {
        return studentNames;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public boolean addStudent(String studentFullName) {

        if (!isFull()) {
            studentNames.add(studentFullName);
            currentStudents++;
            return true;
        }

        return false;
    }

    public boolean addStudent(Student student) {
        return addStudent(student.getFirstName() + " " + student.getLastName());
    }
    
    public boolean removeStudent(String studentFullName) {

        if (studentNames.remove(studentFullName)) {
            currentStudents--;
            return true;
        }

        return false;
    }

  
    public boolean isFull() {
        return currentStudents >= maxStudents;
    }


    @Override
    public int compareTo(Course otherCourse) {
        return this.currentStudents - otherCourse.currentStudents;
    }


    @Override
    public String toString() {
        return "Course Name: " + courseName +
                "\nCourse ID: " + courseID +
                "\nInstructor: " + instructor +
                "\nSection: " + sectionNumber +
                "\nLocation: " + location +
                "\nCurrent Students: " + currentStudents +
                "\nMaximum Students: " + maxStudents ";
    }
}

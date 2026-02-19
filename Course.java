package hw1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Course class stores all course information required by the system.
 * Implements Serializable (Req 12) and Comparable for sorting (Admin Reports).
 */
public class Course implements Serializable, Comparable<Course> {

    private static final long serialVersionUID = 1L;

    private String courseName;
    private String courseID;
    private int maxStudents;
    private int currentStudents;
    private ArrayList<String> studentNames;
    private String instructor;
    private int sectionNumber;
    private String location;

    // Constructor (initial students = 0 as required)
    public Course(String courseName, String courseID, int maxStudents,
                  String instructor, int sectionNumber, String location) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.maxStudents = maxStudents;
        this.currentStudents = 0;
        this.studentNames = new ArrayList<>();
        this.instructor = instructor;
        this.sectionNumber = sectionNumber;
        this.location = location;
    }

    // Getters (Encapsulation)
    public String getCourseName() {
        return courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public int getMaxStudents() {
        return maxStudents;
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

    public int getSectionNumber() {
        return sectionNumber;
    }

    public String getLocation() {
        return location;
    }

    // Setters (Encapsulation)
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Method Overloading (important for design report)
    public boolean addStudent(String studentFullName) {
        if (!isFull()) {
            studentNames.add(studentFullName);
            currentStudents++;
            return true;
        }
        return false;
    }

    // Overloaded version using Student object (Polymorphism-friendly)
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

    // Check if course is full (Req 03 Reports)
    public boolean isFull() {
        return currentStudents >= maxStudents;
    }

    // Required for sorting courses by number of registered students
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
                "\nMaximum Students: " + maxStudents;
    }
}


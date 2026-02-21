package hw1;

import java.io.IOException;
import java.util.ArrayList;

public interface AdminInterface {

    // adds a new course (returns false if duplicate courseId)
    boolean createCourse(Course c);

    // deletes a course by courseId
    boolean deleteCourse(String courseId);

    // edits a course (not name or ID)
    boolean editCourse(String courseId,
                       int maxStudents,
                       String instructor,
                       int section,
                       String location);

    // finds a course by courseId (returns null if not found)
    Course getCourseById(String courseId);

    // registers a student (returns false if username already exists)
    boolean registerStudent(Student s);

    // returns a list of all courses
    ArrayList<Course> viewAllCourses();

    // returns only the full courses
    ArrayList<Course> viewFullCourses();

    // writes full courses to a file (overwrites)
    boolean writeFullCoursesToFile(String filename) throws IOException;

    // returns names of students in a course
    ArrayList<String> viewStudentsInCourse(String courseId);

    // returns all courses a student is in (by first + last name)
    ArrayList<Course> viewCoursesOfStudent(String firstName, String lastName);

    // returns courses sorted by current students
    ArrayList<Course> sortCoursesByCurrentStudents();
}
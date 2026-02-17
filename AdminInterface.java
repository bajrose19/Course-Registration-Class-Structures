package hw1;

import java.io.IOException;
import java.util.ArrayList;

public interface AdminInterface {

    boolean createCourse(Course c);
    boolean deleteCourse(String courseId);

    boolean editCourse(String courseId,
                       int maxStudents,
                       String instructor,
                       int section,
                       String location);

    Course getCourseById(String courseId);

    boolean registerStudent(Student s);

    //Reports
    ArrayList<Course> viewAllCourses();
    ArrayList<Course> viewFullCourses();

    boolean writeFullCoursesToFile(String filename) throws IOException;

    ArrayList<String> viewStudentsInCourse(String courseId);

    ArrayList<Course> viewCoursesOfStudent(String firstName, String lastName);

    ArrayList<Course> sortCoursesByCurrentStudents();
}

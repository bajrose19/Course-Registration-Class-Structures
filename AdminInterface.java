package hw1;

import java.util.ArrayList;

public interface AdminInterface {
    boolean createCourse(Course c);                
    boolean deleteCourse(String courseId);          

    
    boolean editCourse(String courseId,
                       int maxStudents,
                       String instructor,
                       int section,
                       String location);

    Course getCourseById(String courseId);          //display info for a given course (by ID)

    boolean registerStudent(Student s);             //(not assigning to a course)

    ArrayList<Course> viewAllCourses();             //view all courses
    ArrayList<Course> viewFullCourses();            //view all FULL courses
    boolean writeFullCoursesToFile(String filename);//write FULL courses to a file

    ArrayList<String> viewStudentsInCourse(String courseId); //names of students in a specific course

    //courses a given student is registered in (given first + last)
    ArrayList<Course> viewCoursesOfStudent(String firstName, String lastName);

    ArrayList<Course> sortCoursesByCurrentStudents(); //sort courses by current # registered
}
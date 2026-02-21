package hw1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Admin extends User implements AdminInterface {

    //list of all courses
    private final ArrayList<Course> courses;

    //list of all registered students
    private final ArrayList<Student> students;

    //default admin login: Admin / Admin001
    public Admin(ArrayList<Student> students, ArrayList<Course> courses) {
        super("Admin", "Admin001", "System", "Administrator");
        this.students = students;
        this.courses = courses;
    }

    //custom admin login (if driver passes credentials)
    public Admin(String username, String password, String firstName, String lastName,
                 ArrayList<Student> students, ArrayList<Course> courses) {
        super(username, password, firstName, lastName);
        this.students = students;
        this.courses = courses;
    }

    @Override
    //adds a course if courseId is unique
    public boolean createCourse(Course c) {
        if (c == null) return false;

        String newId = c.getCourseID();
        if (newId == null) return false;

        //reject duplicate courseId
        for (Course existing : courses) {
            if (existing != null && newId.equals(existing.getCourseID())) {
                return false;
            }
        }

        courses.add(c);
        return true;
    }

    @Override
    //removes the course with matching courseId
    public boolean deleteCourse(String courseId) {
        if (courseId == null) return false;

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c != null && courseId.equals(c.getCourseID())) {
                courses.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    //edits max, instructor, section, location (not name or ID)
    public boolean editCourse(String courseId, int maxStudents, String instructor, int section, String location) {
        if (courseId == null) return false;

        Course c = getCourseById(courseId);
        if (c == null) return false;

        if (maxStudents > 0) {
            if (maxStudents < c.getCurrentStudents()) return false;
            c.setMaxStudents(maxStudents);
        }

        if (instructor != null && !instructor.trim().isEmpty()) {
            c.setInstructor(instructor.trim());
        }

        if (section > 0) {
            c.setSectionNumber(section);
        }

        if (location != null && !location.trim().isEmpty()) {
            c.setLocation(location.trim());
        }

        return true;
    }

    @Override
    //returns the course with matching courseId (or null)
    public Course getCourseById(String courseId) {
        if (courseId == null) return null;

        for (Course c : courses) {
            if (c != null && courseId.equals(c.getCourseID())) {
                return c;
            }
        }

        return null;
    }

    @Override
    //adds a student if username is unique
    public boolean registerStudent(Student s) {
        if (s == null) return false;

        String newUser = s.getUsername();
        if (newUser == null) return false;

        for (Student existing : students) {
            if (existing != null && existing.getUsername() != null
                    && existing.getUsername().equalsIgnoreCase(newUser)) {
                return false;
            }
        }

        students.add(s);
        return true;
    }

    @Override
    //returns a copy of all courses
    public ArrayList<Course> viewAllCourses() {
        return new ArrayList<>(courses);
    }

    @Override
    //returns only full courses
    public ArrayList<Course> viewFullCourses() {
        ArrayList<Course> full = new ArrayList<>();

        for (Course c : courses) {
            if (c != null && c.isFull()) {
                full.add(c);
            }
        }

        return full;
    }

    @Override
    //writes full courses to a file (overwrites)
    public boolean writeFullCoursesToFile(String filename) throws IOException {
        if (filename == null || filename.trim().isEmpty()) return false;

        ArrayList<Course> full = viewFullCourses();
        ArrayList<String> lines = new ArrayList<>();

        lines.add("Full Courses Report");
        lines.add("-------------------");

        if (full.isEmpty()) {
            lines.add("No courses are currently full.");
        } else {
            for (Course c : full) {
                lines.add(
                    c.getCourseName() + " | " + c.getCourseID() +
                    " | " + c.getCurrentStudents() + "/" + c.getMaxStudents() +
                    " | " + c.getInstructor() +
                    " | Section " + c.getSectionNumber() +
                    " | " + c.getLocation()
                );
            }
        }

        Files.write(Path.of(filename.trim()), lines);
        return true;
    }

    @Override
    //returns student names in a course (or empty list)
    public ArrayList<String> viewStudentsInCourse(String courseId) {
        Course c = getCourseById(courseId);
        if (c == null) return new ArrayList<>();
        return new ArrayList<>(c.getStudentNames());
    }

    @Override
    //returns all courses that contain "first last"
    public ArrayList<Course> viewCoursesOfStudent(String firstName, String lastName) {
        ArrayList<Course> result = new ArrayList<>();

        if (firstName == null || lastName == null) return result;

        String targetName = (firstName.trim() + " " + lastName.trim()).trim();
        if (targetName.isEmpty()) return result;

        for (Course c : courses) {
            if (c != null && c.getStudentNames().contains(targetName)) {
                result.add(c);
            }
        }

        return result;
    }

    @Override
    //returns courses sorted by current students (least to greatest)
    public ArrayList<Course> sortCoursesByCurrentStudents() {
        ArrayList<Course> sorted = new ArrayList<>(courses);
        sorted.sort(null);
        return sorted;
    }

    @Override
    //prints the admin menu
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
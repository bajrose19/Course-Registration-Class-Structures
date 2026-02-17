package hw1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Admin extends User implements AdminInterface {

    private final ArrayList<Course> courses;
    private final ArrayList<Student> students;

    // Homework default Admin account (Req: Admin / Admin001)
    public Admin(ArrayList<Student> students, ArrayList<Course> courses) {
        super("Admin", "Admin001", "System", "Administrator");
        this.students = students;
        this.courses = courses;
    }

    // Flexible constructor (still useful if your driver passes credentials)
    public Admin(String username, String password, String firstName, String lastName,
                 ArrayList<Student> students, ArrayList<Course> courses) {
        super(username, password, firstName, lastName);
        this.students = students;
        this.courses = courses;
    }

    @Override
    public boolean createCourse(Course c) {
        if (c == null) return false;

        String newId = c.getCourseID();
        if (newId == null) return false;

        // courseId must be unique
        for (Course existing : courses) {
            if (existing != null && newId.equals(existing.getCourseID())) {
                return false;
            }
        }

        courses.add(c);
        return true;
    }

    @Override
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
    public boolean editCourse(String courseId, int maxStudents, String instructor, int section, String location) {
        if (courseId == null) return false;

        Course c = getCourseById(courseId);
        if (c == null) return false;

        // Can't edit courseId or courseName (your parameters already enforce that)

        if (maxStudents > 0) {
            // Don't allow max below current enrolled
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
    public boolean registerStudent(Student s) {
        if (s == null) return false;

        String newUser = s.getUsername();
        if (newUser == null) return false;

        for (Student existing : students) {
            if (existing != null && existing.getUsername() != null
                    && existing.getUsername().equalsIgnoreCase(newUser)) {
                return false; // already registered
            }
        }

        students.add(s);
        return true;
    }

    @Override
    public ArrayList<Course> viewAllCourses() {
        return new ArrayList<>(courses);
    }

    @Override
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

        // Overwrites by default
        Files.write(Path.of(filename.trim()), lines);
        return true;
    }

    @Override
    public ArrayList<String> viewStudentsInCourse(String courseId) {
        Course c = getCourseById(courseId);
        if (c == null) return new ArrayList<>();
        return new ArrayList<>(c.getStudentNames());
    }

    @Override
    public ArrayList<Course> viewCoursesOfStudent(String firstName, String lastName) {
        ArrayList<Course> result = new ArrayList<>();

        if (firstName == null || lastName == null) return result;

        String targetName = (firstName.trim() + " " + lastName.trim()).trim();
        if (targetName.isEmpty()) return result;

        for (Course c : courses) {
            if (c != null && c.getStudentNames() != null && c.getStudentNames().contains(targetName)) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Course> sortCoursesByCurrentStudents() {
        ArrayList<Course> sorted = new ArrayList<>(courses);
        sorted.sort(null); // uses Course.compareTo because Course implements Comparable<Course>
        return sorted;
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

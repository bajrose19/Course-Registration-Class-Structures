package hw1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Admin extends User implements AdminInterface {

    // private fields that Admin uses to manage the whole system
    // courses = list of all courses in the system
    // students = list of all registered students in the system
    private final ArrayList<Course> courses;
    private final ArrayList<Student> students;

    // homework default Admin account (Req: Admin / Admin001)
    // this constructor is used when you want ONE fixed admin login
    // it also connects Admin to the main shared lists (courses + students)
    public Admin(ArrayList<Student> students, ArrayList<Course> courses) {
        super("Admin", "Admin001", "System", "Administrator");
        this.students = students;
        this.courses = courses;
    }

    // flexible constructor (useful if your driver passes credentials)
    // still connects Admin to the shared lists (courses + students)
    public Admin(String username, String password, String firstName, String lastName,
                 ArrayList<Student> students, ArrayList<Course> courses) {
        super(username, password, firstName, lastName);
        this.students = students;
        this.courses = courses;
    }

    @Override
    // createCourse adds a new course into the system
    // returns false if:
    // - the Course object is null
    // - the course ID is null
    // - another course already has the same course ID (must be unique)
    // returns true if the course is successfully added
    public boolean createCourse(Course c) {
        if (c == null) return false;

        String newId = c.getCourseID();
        if (newId == null) return false;

        // courseId must be unique
        for (Course existing : courses) {
            // if existing course is not null and has same ID, reject it
            if (existing != null && newId.equals(existing.getCourseID())) {
                return false;
            }
        }

        // if everything is valid, add the course to the list
        courses.add(c);
        return true;
    }

    @Override
    // deleteCourse removes a course from the system based on courseId
    // returns true if the course is found and removed
    // returns false if:
    // - courseId is null
    // - courseId is not found in the list
    public boolean deleteCourse(String courseId) {
        if (courseId == null) return false;

        // loop through the list and remove the first matching course
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);

            // compare IDs safely: courseId.equals(...)
            if (c != null && courseId.equals(c.getCourseID())) {
                courses.remove(i);
                return true;
            }
        }

        // courseId was never found
        return false;
    }

    @Override
    // editCourse updates a course's editable fields (NOT name or ID)
    // returns false if:
    // - courseId is null
    // - the course is not found
    // - maxStudents is less than currentStudents (would cause invalid enrollment)
    // otherwise updates fields (only when valid values are given) and returns true
    public boolean editCourse(String courseId, int maxStudents, String instructor, int section, String location) {
        if (courseId == null) return false;

        // find the course first
        Course c = getCourseById(courseId);
        if (c == null) return false;

        // update maxStudents (only if user gave a positive value)
        if (maxStudents > 0) {
            // don't allow max below current enrolled students
            if (maxStudents < c.getCurrentStudents()) return false;
            c.setMaxStudents(maxStudents);
        }

        // update instructor (only if non-empty)
        if (instructor != null && !instructor.trim().isEmpty()) {
            c.setInstructor(instructor.trim());
        }

        // update section number (only if positive)
        if (section > 0) {
            c.setSectionNumber(section);
        }

        // update location (only if non-empty)
        if (location != null && !location.trim().isEmpty()) {
            c.setLocation(location.trim());
        }

        return true;
    }

    @Override
    // getCourseById searches for a course by its course ID
    // returns the Course if found
    // returns null if:
    // - courseId is null
    // - no course matches
    public Course getCourseById(String courseId) {
        if (courseId == null) return null;

        // loop through all courses and compare IDs
        for (Course c : courses) {
            if (c != null && courseId.equals(c.getCourseID())) {
                return c;
            }
        }

        return null;
    }

    @Override
    // registerStudent adds a student to the system student list
    // returns false if:
    // - Student object is null
    // - student's username is null
    // - username already exists (case-insensitive)
    // returns true if student is successfully added
    public boolean registerStudent(Student s) {
        if (s == null) return false;

        String newUser = s.getUsername();
        if (newUser == null) return false;

        // check for duplicate username
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
    // viewAllCourses returns a COPY of all courses
    // returning a copy prevents other classes from accidentally changing the real list
    public ArrayList<Course> viewAllCourses() {
        return new ArrayList<>(courses);
    }

    @Override
    // viewFullCourses returns a list of courses that are full
    // a course is full if currentStudents >= maxStudents (Course.isFull())
    public ArrayList<Course> viewFullCourses() {
        ArrayList<Course> full = new ArrayList<>();

        // loop through courses and check if full
        for (Course c : courses) {
            if (c != null && c.isFull()) {
                full.add(c);
            }
        }

        return full;
    }

    @Override
    // writeFullCoursesToFile writes the FULL courses report into a file (OVERWRITES the file)
    // returns false if filename is null/blank
    // returns true if file write happens successfully
    // throws IOException because Files.write can fail (like invalid path)
    public boolean writeFullCoursesToFile(String filename) throws IOException {
        if (filename == null || filename.trim().isEmpty()) return false;

        // get all full courses first
        ArrayList<Course> full = viewFullCourses();

        // create the lines we want to write into the file
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Full Courses Report");
        lines.add("-------------------");

        if (full.isEmpty()) {
            lines.add("No courses are currently full.");
        } else {
            // write each full course as 1 line
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

        // overwrites the file by default
        Files.write(Path.of(filename.trim()), lines);
        return true;
    }

    @Override
    // viewStudentsInCourse returns a list of student full names in a specific course
    // returns empty list if the courseId is invalid or the course doesn't exist
    public ArrayList<String> viewStudentsInCourse(String courseId) {
        Course c = getCourseById(courseId);

        // if course doesn't exist, return empty list instead of null
        if (c == null) return new ArrayList<>();

        // return a copy so caller can't edit the original roster
        return new ArrayList<>(c.getStudentNames());
    }

    @Override
    // viewCoursesOfStudent returns all courses that contain the student full name
    // student is identified by firstName + " " + lastName (matches your Course roster storage)
    // returns empty list if names are null/blank
    public ArrayList<Course> viewCoursesOfStudent(String firstName, String lastName) {
        ArrayList<Course> result = new ArrayList<>();

        // invalid input -> return empty list
        if (firstName == null || lastName == null) return result;

        // build the name exactly how Course stores it, and trim to avoid spacing issues
        String targetName = (firstName.trim() + " " + lastName.trim()).trim();
        if (targetName.isEmpty()) return result;

        // scan every course to see if the roster contains that student name
        for (Course c : courses) {
            if (c != null && c.getStudentNames() != null && c.getStudentNames().contains(targetName)) {
                result.add(c);
            }
        }

        return result;
    }

    @Override
    // sortCoursesByCurrentStudents returns a sorted COPY of courses
    // sorting is least -> greatest based on currentStudents (Course.compareTo)
    public ArrayList<Course> sortCoursesByCurrentStudents() {
        ArrayList<Course> sorted = new ArrayList<>(courses);

        // uses Course.compareTo because Course implements Comparable<Course>
        sorted.sort(null);

        return sorted;
    }

    @Override
    // displayMenu prints out the admin menu options
    // (your driver can read the choice and call the correct method)
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

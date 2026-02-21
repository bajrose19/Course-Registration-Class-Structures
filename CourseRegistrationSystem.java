package hw1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CourseRegistrationSystem
 * Handles startup, login routing, menu loops, CSV loading, and serialization
 * Displays login menu, routes users to menu, loads course data from a CSV file, loads/saves serialized data
 */
public class CourseRegistrationSystem {

    static ArrayList<Course>  courses  = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("   Welcome to the Course Registration   ");
        System.out.println("              System                    ");

        // try to load serialized data first; fall back to CSV
        if (!loadSerializedData()) {
            readCoursesFromCSV("MyUniversityCourses.csv");
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

       //keeps running until user selects Exit
        while (running) {
            System.out.println("\nAre you an Admin or a Student?");
            System.out.println("  1. Admin");
            System.out.println("  2. Student");
            System.out.println("  3. Exit");
            System.out.print("Enter choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    //route to admin login and menu
                    handleAdminLogin(scanner);
                    break;
                case "2":
                    //route to student login
                    handleStudentLogin(scanner);
                    break;
                case "3":
                    //signal the loop to stop
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }

        // save state on exit
        saveSerializedData();
        System.out.println("Goodbye!");
    }

   
    // LOGIN HANDLERS
  

    static void handleAdminLogin(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

       // create an admin instance with access to shared students and courses lists
        Admin admin = new Admin(students, courses);

     // validate credentials using the inherited login() method from User
        if (admin.login(username, password)) {
            System.out.println("\nAdmin login successful. Welcome, " + admin.getFullName() + "!");
            runAdminMenu(scanner, admin);
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }
    // offers student two options: login or create a new account
    static void handleStudentLogin(Scanner scanner) {
        System.out.println("\nStudent Login");
        System.out.println("  1. Login with existing account");
        System.out.println("  2. Create new account");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

           // search for matching credentials
            Student found = findStudent(username, password);
            if (found != null) {
                System.out.println("\nStudent login successful. Welcome, " + found.getFullName() + "!");
                runStudentMenu(scanner, found);
            } else {
                System.out.println("Invalid student credentials.");
            }

        } else if (choice.equals("2")) {
            //collect info for new account
            System.out.print("First name: ");
            String first = scanner.nextLine().trim();
            System.out.print("Last name: ");
            String last = scanner.nextLine().trim();
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Password (min 8 chars): ");
            String password = scanner.nextLine().trim();

             // minimum password length is 8
            if (password.length() < 8) {
                System.out.println("Password too short. Account not created.");
                return;
            }

          // create the new student object with access to the shared course list
            Student newStudent = new Student(username, password, first, last, courses);
            Admin tempAdmin = new Admin(students, courses);

            if (tempAdmin.registerStudent(newStudent)) {
                System.out.println("Account created! Welcome, " + newStudent.getFullName() + "!");
                runStudentMenu(scanner, newStudent);
            } else {
                System.out.println("Username already taken. Please try again.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    // ADMIN MENU LOOP
    // each case delegates to the corresponding admin method
 

    static void runAdminMenu(Scanner scanner, Admin admin) {
        boolean active = true;
        while (active) {
            admin.displayMenu();
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {

                //  1. Create course 
                case "1": {
                    System.out.print("Course Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Course ID: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Max Students: ");
                    int max = readInt(scanner);
                    System.out.print("Instructor: ");
                    String instructor = scanner.nextLine().trim();
                    System.out.print("Section Number: ");
                    int section = readInt(scanner);
                    System.out.print("Location: ");
                    String location = scanner.nextLine().trim();

                    Course c = new Course(name, id, max, instructor, section, location);
                    System.out.println(admin.createCourse(c)
                        ? "Course created successfully."
                        : "Failed: Course ID may already exist or input was invalid.");
                    break;
                }

                //  2. Delete course 
                case "2": {
                    System.out.print("Enter Course ID to delete: ");
                    String id = scanner.nextLine().trim();
                    System.out.println(admin.deleteCourse(id)
                        ? "Course deleted."
                        : "Course not found.");
                    break;
                }

                // 3. Edit course 
                case "3": {
                    System.out.print("Course ID to edit: ");
                    String id = scanner.nextLine().trim();

                    System.out.print("New Max Students (0 to skip): ");
                    int max = readInt(scanner);
                    System.out.print("New Instructor (leave blank to skip): ");
                    String instructor = scanner.nextLine().trim();
                    System.out.print("New Section Number (0 to skip): ");
                    int section = readInt(scanner);
                    System.out.print("New Location (leave blank to skip): ");
                    String location = scanner.nextLine().trim();

                    System.out.println(admin.editCourse(id, max, instructor, section, location)
                        ? "Course updated."
                        : "Failed: Check ID, or max students may be below current enrollment.");
                    break;
                }

                //  4. Display course by ID 
                case "4": {
                    System.out.print("Enter Course ID: ");
                    String id = scanner.nextLine().trim();
                    Course c = admin.getCourseById(id);
                    System.out.println(c != null ? c : "Course not found.");
                    break;
                }

                // 5. Register a student 
                case "5": {
                    System.out.print("First name: ");
                    String first = scanner.nextLine().trim();
                    System.out.print("Last name: ");
                    String last = scanner.nextLine().trim();
                    System.out.print("Username: ");
                    String username = scanner.nextLine().trim();
                    System.out.print("Password (min 8 chars): ");
                    String password = scanner.nextLine().trim();

                    if (password.length() < 8) {
                        System.out.println("Password too short. Student not registered.");
                        break;
                    }

                    Student s = new Student(username, password, first, last, courses);
                    System.out.println(admin.registerStudent(s)
                        ? "Student registered: " + s.getFullName()
                        : "Failed: Username already exists.");
                    break;
                }

                //  6. View all courses 
                case "6": {
                    ArrayList<Course> all = admin.viewAllCourses();
                    if (all.isEmpty()) {
                        System.out.println("No courses in the system.");
                    } else {
                        System.out.println("\n All Courses");
                        for (Course c : all) {
                            System.out.println(c);
                            
                        }
                    }
                    break;
                }

                // 7. View full courses 
                case "7": {
                    ArrayList<Course> full = admin.viewFullCourses();
                    if (full.isEmpty()) {
                        System.out.println("No courses are currently full.");
                    } else {
                        System.out.println("\n Full Courses");
                        for (Course c : full) {
                            System.out.println(c.getCourseName() + " (" + c.getCourseID() + ")" +
                                               " — " + c.getCurrentStudents() + "/" + c.getMaxStudents());
                        }
                    }
                    break;
                }

                //  8. Write full courses to file 
                case "8": {
                    System.out.print("Enter filename (e.g. full_courses.txt): ");
                    String filename = scanner.nextLine().trim();
                    try {
                        System.out.println(admin.writeFullCoursesToFile(filename)
                            ? "Report written to: " + filename
                            : "Failed: Filename was blank.");
                    } catch (Exception e) {
                        System.out.println("Error writing file: " + e.getMessage());
                    }
                    break;
                }

                // 9. View students in a course 
                case "9": {
                    System.out.print("Enter Course ID: ");
                    String id = scanner.nextLine().trim();
                    ArrayList<String> names = admin.viewStudentsInCourse(id);
                    if (names.isEmpty()) {
                        System.out.println("No students found (or course does not exist).");
                    } else {
                        System.out.println("\nStudents in course " + id + ":");
                        for (String name : names) System.out.println("  - " + name);
                    }
                    break;
                }

                //  10. View courses of a student
                case "10": {
                    System.out.print("Student first name: ");
                    String first = scanner.nextLine().trim();
                    System.out.print("Student last name: ");
                    String last = scanner.nextLine().trim();
                    ArrayList<Course> studentCourses = admin.viewCoursesOfStudent(first, last);
                    if (studentCourses.isEmpty()) {
                        System.out.println("No courses found for " + first + " " + last + ".");
                    } else {
                        System.out.println("\nCourses for " + first + " " + last + ":");
                        for (Course c : studentCourses) {
                            System.out.println("  - " + c.getCourseName() + " (" + c.getCourseID() + ")");
                        }
                    }
                    break;
                }

                //  11. Sort courses by enrollment
                case "11": {
                    ArrayList<Course> sorted = admin.sortCoursesByCurrentStudents();
                    System.out.println("\n Courses Sorted by Enrollment (Least → Most)");
                    for (Course c : sorted) {
                        System.out.println(c.getCourseName() + " (" + c.getCourseID() + ")" +
                                           " — " + c.getCurrentStudents() + "/" + c.getMaxStudents());
                    }
                    break;
                }

                //  12. Exit 
                case "12":
                    active = false;
                    System.out.println("Returning to main menu:");
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1–12.");
            }
        }
    }

    // STUDENT MENU LOOP


    static void runStudentMenu(Scanner scanner, Student student) {
        boolean active = true;
        while (active) {
            student.displayMenu();
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {

                //  1. View all courses 
                case "1": {
                    ArrayList<Course> all = student.viewAllCourses();
                    if (all.isEmpty()) {
                        System.out.println("No courses available.");
                    } else {
                        System.out.println("\n--- All Courses ---");
                        for (int i = 0; i < all.size(); i++) {
                            System.out.println("[" + i + "] " + all.get(i).getCourseName() +
                                               " (" + all.get(i).getCourseID() + ")" +
                                               " — Sec " + all.get(i).getSectionNumber() +
                                               " — " + all.get(i).getCurrentStudents() +
                                               "/" + all.get(i).getMaxStudents() +
                                               (all.get(i).isFull() ? " [FULL]" : ""));
                        }
                    }
                    break;
                }

                // 2. View available (not full) courses
                case "2": {
                    ArrayList<Course> available = student.viewAvailableCourses();
                    if (available.isEmpty()) {
                        System.out.println("No available courses right now.");
                    } else {
                        System.out.println("\n--- Available Courses ---");
                        for (Course c : available) {
                            System.out.println("  " + c.getCourseName() +
                                               " (" + c.getCourseID() + ")" +
                                               " — Sec " + c.getSectionNumber() +
                                               " — " + c.getCurrentStudents() + "/" + c.getMaxStudents());
                        }
                    }
                    break;
                }

                //  3. Register for a course 
                case "3": {
                    System.out.print("Enter Course ID to register: ");
                    String id = scanner.nextLine().trim();

                    // Collect all sections matching this ID
                    ArrayList<Course> matches = new ArrayList<>();
                    for (Course c : courses) {
                        if (c != null && id.equals(c.getCourseID())) matches.add(c);
                    }

                    if (matches.isEmpty()) {
                        System.out.println("Course not found.");
                        break;
                    }

                    Course target;
                    if (matches.size() == 1) {
                        target = matches.get(0);
                    } else {
                        System.out.println("Multiple sections available for " + matches.get(0).getCourseName() + ":");
                        for (Course c : matches) {
                            System.out.println("  Sec " + c.getSectionNumber() +
                                               " | " + c.getInstructor() +
                                               " | " + c.getLocation() +
                                               " | " + c.getCurrentStudents() + "/" + c.getMaxStudents() +
                                               (c.isFull() ? " [FULL]" : ""));
                        }
                        System.out.print("Enter section number: ");
                        int sec = readInt(scanner);
                        target = null;
                        for (Course c : matches) {
                            if (c.getSectionNumber() == sec) { target = c; break; }
                        }
                        if (target == null) { System.out.println("Section not found."); break; }
                    }

                    System.out.println(student.courseRegistration(target)
                        ? "Successfully registered in: " + target.getCourseName() + " Sec " + target.getSectionNumber()
                        : "Registration failed (already registered, or course is full).");
                    break;
                }

                //  4. Withdraw from a course 
                case "4": {
                    ArrayList<Course> myCourses = student.viewRegisteredCourses();
                    if (myCourses.isEmpty()) {
                        System.out.println("You are not registered in any courses.");
                        break;
                    }

                    System.out.println("Your courses:");
                    for (int i = 0; i < myCourses.size(); i++) {
                        System.out.println("[" + i + "] " + myCourses.get(i).getCourseName() +
                                           " (" + myCourses.get(i).getCourseID() + ")");
                    }

                    System.out.print("Enter Course ID to withdraw from: ");
                    String id = scanner.nextLine().trim();

                    Course target = null;
                    for (Course c : myCourses) {
                        if (id.equals(c.getCourseID())) {
                            target = c;
                            break;
                        }
                    }

                    if (target == null) {
                        System.out.println("You are not registered in course: " + id);
                    } else {
                        System.out.println(student.courseWithdrawal(target)
                            ? "Successfully withdrawn from: " + target.getCourseName()
                            : "Withdrawal failed.");
                    }
                    break;
                }

                //  5. View my registered courses
                case "5": {
                    ArrayList<Course> myCourses = student.viewRegisteredCourses();
                    if (myCourses.isEmpty()) {
                        System.out.println("You are not registered in any courses.");
                    } else {
                        System.out.println("\n--- Your Registered Courses ---");
                        for (Course c : myCourses) {
                            System.out.println("  - " + c.getCourseName() +
                                               " (" + c.getCourseID() + ")" +
                                               " | Sec " + c.getSectionNumber() +
                                               " | " + c.getInstructor() +
                                               " | " + c.getLocation());
                        }
                    }
                    break;
                }

                //  6. Exit 
                case "6":
                    active = false;
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1–6.");
            }
        }
    }

    // UTILITY METHODS

    // Find a student in the master list by credentials
    static Student findStudent(String username, String password) {
        for (Student s : students) {
            if (s != null && s.login(username, password)) return s;
        }
        return null;
    }

    //  keeps asking until a valid int is entered
    static int readInt(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    // CSV LOADER 

    public static void readCoursesFromCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // skip header row

            while ((line = br.readLine()) != null) {
                // Split on comma but respect quoted fields (e.g. locations with commas)
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (data.length < 8) continue; // skip malformed rows

                String courseName = data[0].trim();
                String courseID   = data[1].trim();
                int    maxStudents= Integer.parseInt(data[2].trim());
                // data[3] = Current_Students (always 0 on load)
                // data[4] = List_Of_Names    (NULL on load)
                String instructor = data[5].trim();
                int    section    = Integer.parseInt(data[6].trim());
                String location   = data[7].trim();

                // Add directly — the CSV is the authoritative source and may have
                // multiple sections under the same Course_Id (e.g. CSCI-GA.3033)
                courses.add(new Course(courseName, courseID, maxStudents, instructor, section, location));
            }
            System.out.println("Courses loaded from CSV: " + courses.size() + " courses.");

        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found: " + fileName + ". Starting with empty course list.");
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number in CSV: " + e.getMessage());
        }
    }

    // SERIALIZATION 

    @SuppressWarnings("unchecked")
    public static boolean loadSerializedData() {
        boolean coursesLoaded  = false;
        boolean studentsLoaded = false;

        // Load courses
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("courses.ser"))) {
            courses = (ArrayList<Course>) ois.readObject();
            coursesLoaded = true;
        } catch (IOException | ClassNotFoundException e) {
            // no saved courses — will fall back to CSV
        }

        // Load students
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.ser"))) {
            students = (ArrayList<Student>) ois.readObject();
            studentsLoaded = true;
        } catch (IOException | ClassNotFoundException e) {
            // no saved students so start fresh
        }

        if (coursesLoaded) {
            System.out.println("Serialized data loaded (" + courses.size() + " courses, " +
                               students.size() + " students).");
        }

        return coursesLoaded;
    }

    public static void saveSerializedData() {
        // Save courses
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("courses.ser"))) {
            oos.writeObject(courses);
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }

        // Save students
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.ser"))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }

        System.out.println("Data saved successfully.");
    }
}


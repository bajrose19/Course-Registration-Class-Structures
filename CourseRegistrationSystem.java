package hw1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseRegistrationSystem {

    static ArrayList<Course> courses = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        // Try to load saved data first
        if (!loadSerializedData()) {
            readCoursesFromCSV("MyUniversityCourses.csv");
        }

        startSystem();

        // Save data when program exits
        saveSerializedData();
    }

    // LOAD CSV (PUT CSV IN PROJECT ROOT FOLDER)
    public static void readCoursesFromCSV(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                String courseName = data[0];
                String courseID = data[1];
                int maxStudents = Integer.parseInt(data[2]);
                String instructor = data[5];
                int section = Integer.parseInt(data[6]);
                String location = data[7];

                Course course = new Course(
                        courseName,
                        courseID,
                        maxStudents,
                        instructor,
                        section,
                        location
                );

                courses.add(course);
            }

            br.close();
            System.out.println("Courses loaded from CSV.");

        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing CSV numbers: " + e.getMessage());
        }
    }

    // SIMPLE MENU SYSTEM
    public static void startSystem() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Course Registration System");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Student student = new Student(username, password, "Default", "Student");
        students.add(student);

        int choice = 0;

        do {
            System.out.println("\n1. View All Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Withdraw from Course");
            System.out.println("4. View My Courses");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            // Prevent crash if user enters non-integer
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (courses.isEmpty()) {
                        System.out.println("No courses available.");
                    } else {
                        for (int i = 0; i < courses.size(); i++) {
                            System.out.println(i + ": " + courses.get(i));
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter course index to register: ");
                    if (scanner.hasNextInt()) {
                        int regIndex = scanner.nextInt();

                        if (regIndex >= 0 && regIndex < courses.size()) {
                            if (student.courseRegistration(courses.get(regIndex))) {
                                System.out.println("Registered successfully!");
                            } else {
                                System.out.println("Registration failed (course may be full or already registered).");
                            }
                        } else {
                            System.out.println("Invalid course index.");
                        }
                    } else {
                        System.out.println("Invalid input.");
                        scanner.next();
                    }
                    break;

                case 3:
                    System.out.print("Enter course index to withdraw: ");
                    if (scanner.hasNextInt()) {
                        int wIndex = scanner.nextInt();

                        if (wIndex >= 0 && wIndex < courses.size()) {
                            if (student.courseWithdrawal(courses.get(wIndex))) {
                                System.out.println("Withdrawn successfully!");
                            } else {
                                System.out.println("Withdrawal failed (not registered in this course).");
                            }
                        } else {
                            System.out.println("Invalid course index.");
                        }
                    } else {
                        System.out.println("Invalid input.");
                        scanner.next();
                    }
                    break;

                case 4:
                    System.out.println("Your Courses:");
                    ArrayList<Course> myCourses = student.viewRegisteredCourses();
                    if (myCourses.isEmpty()) {
                        System.out.println("You are not registered in any courses.");
                    } else {
                        for (Course c : myCourses) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 5:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }

        } while (choice != 5);

        scanner.close();
    }

    // SAVE DATA (Serialization)
    public static void saveSerializedData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream("courses.ser"));
            oos.writeObject(courses);
            oos.close();
            System.out.println("Data saved.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // LOAD DATA (Deserialization)
    @SuppressWarnings("unchecked")
    public static boolean loadSerializedData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("courses.ser"));
            courses = (ArrayList<Course>) ois.readObject();
            ois.close();
            System.out.println("Serialized data loaded.");
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved data found. Loading from CSV instead.");
            return false;
        }
    }
}

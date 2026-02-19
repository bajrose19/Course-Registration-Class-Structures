package hw1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseRegistrationSystem {

    static ArrayList<Course> courses = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();
    static Admin admin = new Admin();

    public static void main(String[] args) {

        if (!loadSerializedData()) {
            readCoursesFromCSV("MyUniversityCourses.csv");
        }

        startSystem();

        saveSerializedData();
    }

    public static void startSystem() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Admin Login");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                adminMenu(sc);
            } else if (choice == 2) {
                studentMenu(sc);
            } else {
                break;
            }
        }
    }

    public static void adminMenu(Scanner sc) {
        System.out.print("Enter admin username: ");
        String u = sc.nextLine();
        System.out.print("Enter admin password: ");
        String p = sc.nextLine();

        if (!admin.login(u, p)) {
            System.out.println("Invalid admin credentials.");
            return;
        }

        while (true) {
            System.out.println("\n1. View All Courses");
            System.out.println("2. Create Course");
            System.out.println("3. Delete Course");
            System.out.println("4. View Full Courses");
            System.out.println("5. Back");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                admin.displayAllCourses(courses);
            } else if (choice == 2) {
                System.out.print("Course Name: ");
                String name = sc.nextLine();
                System.out.print("Course ID: ");
                String id = sc.nextLine();
                System.out.print("Max Students: ");
                int max = sc.nextInt();
                sc.nextLine();
                System.out.print("Instructor: ");
                String inst = sc.nextLine();
                System.out.print("Section: ");
                int sec = sc.nextInt();
                sc.nextLine();
                System.out.print("Location: ");
                String loc = sc.nextLine();

                Course c = new Course(name, id, max, inst, sec, loc);
                admin.createCourse(courses, c);

            } else if (choice == 3) {
                System.out.print("Course ID: ");
                String id = sc.nextLine();
                System.out.print("Section: ");
                int sec = sc.nextInt();
                sc.nextLine();
                admin.deleteCourse(courses, id, sec);

            } else if (choice == 4) {
                admin.displayFullCourses(courses);
            } else {
                break;
            }
        }
    }

    public static void studentMenu(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Student student = new Student(username, password, "First", "Last");
        students.add(student);

        while (true) {
            System.out.println("\n1. View All Courses");
            System.out.println("2. View Available Courses");
            System.out.println("3. Register Course");
            System.out.println("4. Withdraw Course");
            System.out.println("5. View My Courses");
            System.out.println("6. Back");

            int choice = sc.nextInt();

            if (choice == 1) {
                student.viewAllCourses(courses);
            } else if (choice == 2) {
                student.viewAvailableCourses(courses);
            } else if (choice == 3) {
                System.out.print("Enter course index: ");
                int index = sc.nextInt();
                student.registerCourse(courses.get(index));
            } else if (choice == 4) {
                System.out.print("Enter course index: ");
                int index = sc.nextInt();
                student.withdrawCourse(courses.get(index));
            } else if (choice == 5) {
                student.viewRegisteredCourses();
            } else {
                break;
            }
        }
    }

    public static void readCoursesFromCSV(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                Course c = new Course(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2]),
                        data[5],
                        Integer.parseInt(data[6]),
                        data[7]
                );
                courses.add(c);
            }
            br.close();
            System.out.println("Courses loaded from CSV.");

        } catch (Exception e) {
            System.out.println("CSV Load Error: " + e.getMessage());
        }
    }

    public static void saveSerializedData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream("courses.ser"));
            oos.writeObject(courses);
            oos.close();
        } catch (Exception e) {
            System.out.println("Save error.");
        }
    }

    public static boolean loadSerializedData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("courses.ser"));
            courses = (ArrayList<Course>) ois.readObject();
            ois.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


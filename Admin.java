import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Admin extends User {

    public Admin(String username) {
        super(username);
    }

    @Override
    public void navigate(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("[ 1 ] - TEACHER");
            System.out.println("[ 2 ] - STUDENT");
            System.out.println("[ 3 ] - GENERATE REPORT");
            System.out.println("[ 0 ] - LOGOUT");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> teacherManagement(scanner);
                case 2 -> studentManagement(scanner);
                case 3 -> generateReport();
                case 0 -> System.out.println("Returning to login...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void manageCourses(Scanner scanner) {
        System.out.print("Enter course name: ");
        scanner.nextLine(); // Consume newline
        String courseName = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = new Course(courseName, courseCode);
        Main.getCourses().add(course);
        System.out.println("Course added successfully.");
    }

    private static void viewGrades(Scanner scanner) {
        System.out.print("Enter student ID to view grades: ");
        String studentId = scanner.nextLine();
        Student student = findStudentById(studentId);
        if (student != null) {
            student.getGrades().forEach((course, grade) -> System.out.println(course.getName() + ": " + grade));
        } else {
            System.out.println("Student not found.");
        }
    }

    private static Student findStudentById(String studentId) {
        for (Student student : Main.getStudents()) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null; // Return null if no student is found
    }

    public static void generateReport() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"))) {
            writer.write("Teachers:\n");
            for (Teacher teacher : Main.getTeachers()) {
                writer.write("Name: " + teacher.getName() + ", ID: " + teacher.getId() + ", Subjects: " + teacher.getSubjects() + "\n");
            }

            writer.write("\nStudents:\n");
            for (Student student : Main.getStudents()) {
                writer.write("Name: " + student.getName() + ", ID: " + student.getId() + ", Grades: " + student.getGrades() + "\n");
            }

            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the report.");
            e.printStackTrace();
        }
    }

    public static void teacherManagement(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n===============================");
            System.out.println("===== TEACHER MANAGEMENT ======");
            System.out.println("===============================");
            System.out.println("[ 1 ] - VIEW ALL TEACHERS");
            System.out.println("[ 2 ] - ADD TEACHER");
            System.out.println("[ 3 ] - REMOVE TEACHER");
            System.out.println("[ 0 ] - BACK");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            Main.ClearScreen();

            switch (choice) {
                case 1 -> Main.viewAllTeachers();
                case 2 -> Teacher.addTeacher(scanner);
                case 3 -> Teacher.removeTeacher(scanner);
                case 0 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public static void studentManagement(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n===============================");
            System.out.println("===== STUDENT MANAGEMENT ======");
            System.out.println("===============================");
            System.out.println("[ 1 ] - VIEW ALL STUDENTS");
            System.out.println("[ 2 ] - ADD STUDENT");
            System.out.println("[ 3 ] - REMOVE STUDENT");
            System.out.println("[ 0 ] - BACK");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            Main.ClearScreen();

            switch (choice) {
                case 1 -> Main.viewAllStudents();
                case 2 -> addStudent(scanner);
                case 3 -> removeStudent(scanner);
                case 0 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Main.getStudents().add(new Student(name, id));
        System.out.println("Student added successfully.");
    }

    private static void removeStudent(Scanner scanner) {
        System.out.print("Enter student ID to remove: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student != null) {
            Main.getStudents().remove(student);
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }
}

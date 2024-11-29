import java.util.Scanner;

public class Admin extends User {

    public Admin(String username) {
        super(username);
    }

    @Override
    public void navigate(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View All Teachers");
            System.out.println("2. Manage Students");
            System.out.println("3. Manage Teachers");
            System.out.println("4. Manage Courses");
            System.out.println("5. View Grades");
            System.out.println("6. Generate Report");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> Main.viewAllTeachers();
                case 2 -> Main.viewAllStudents();
                case 3 -> Main.viewAllTeachers();
                case 4 -> manageCourses(scanner);
                case 5 -> viewGrades(scanner);
                case 6 -> generateReport();
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

    private static void generateReport() {
        System.out.println("Generating report...");
        // Implement the report generation logic here
    }
}

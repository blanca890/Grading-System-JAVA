import java.io.*;
import java.util.*;

public class Main {

    // Store data in memory using collections
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();

    // Predefined credentials for simplicity
    private static final Map<String, String> userCredentials = new HashMap<>();
    private static final Map<String, String> userRoles = new HashMap<>();

    static {
        // Admin credentials
        userCredentials.put("admin", "admin1");
        userRoles.put("admin", "Admin");

        // Example teacher credentials
        userCredentials.put("teacher", "teacher1");
        userRoles.put("teacher", "Teacher");
        userCredentials.put("teacher2", "teacher456");
        userRoles.put("teacher2", "Teacher");

        // Example student credentials
        userCredentials.put("student1", "student123");
        userRoles.put("student1", "Student");
        userCredentials.put("student2", "student456");
        userRoles.put("student2", "Student");

        // Adding example courses
        courses.add(new Course("Mathematics", "MATH101"));
        courses.add(new Course("Science", "SCI101"));
        courses.add(new Course("History", "HIST101"));

        // Adding example students and their grades
        Student student1 = new Student("Student One", "student1");
        student1.addGrade(courses.get(0), "A");
        student1.addGrade(courses.get(1), "B");
        students.add(student1);

        Student student2 = new Student("Student Two", "student2");
        student2.addGrade(courses.get(1), "A");
        student2.addGrade(courses.get(2), "B");
        students.add(student2);

        // Adding example teachers
        teachers.add(new Teacher("Teacher One", "teacher"));
        teachers.add(new Teacher("Teacher Two", "teacher2"));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=== Welcome to the Grading System ===");
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            if (authenticateUser(username, password)) {
                String role = userRoles.get(username);
                switch (role) {
                    case "Admin" -> adminInterface(scanner);
                    case "Teacher" -> teacherInterface(scanner, username);
                    case "Student" -> studentInterface(scanner, username);
                    default -> System.out.println("Invalid role. Exiting system.");
                }
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }

    private static boolean authenticateUser(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    // Admin Interface
    private static void adminInterface(Scanner scanner) {
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

            clearScreen();

            switch (choice) {
                case 1 -> viewAllTeachers();
                case 2 -> manageStudents(scanner);
                case 3 -> manageTeachers(scanner);
                case 4 -> manageCourses(scanner);
                case 5 -> viewGrades();
                case 6 -> generateReport();
                case 0 -> System.out.println("Returning to login...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        
    }

    // Teacher Interface
    private static void teacherInterface(Scanner scanner, String teacherId) {
        System.out.println("\nWelcome Teacher!");
        Teacher teacher = findTeacherById(teacherId);

        if (teacher != null) {
            int choice;
            do {
                System.out.println("\n=== Teacher Menu ===");
                System.out.println("1. View All Students");
                System.out.println("2. Assign Grades");
                System.out.println("3. View Grades");
                System.out.println("0. Logout");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                clearScreen();

                switch (choice) {
                    case 1 -> viewAllStudents();
                    case 2 -> assignGrades(scanner);
                    case 3 -> viewGrades();
                    case 0 -> System.out.println("Returning to login...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 0);
        } else {
            System.out.println("Teacher not found. Exiting system.");
        }
    }

    // Student Interface
    private static void studentInterface(Scanner scanner, String studentId) {
        System.out.println("\nWelcome Student!");
        Student student = findStudentById(studentId);

        if (student != null) {
            int choice;
            do {
                System.out.println("\n=== Student Menu ===");
                System.out.println("1. View Grades");
                System.out.println("0. Logout");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                clearScreen();

                switch (choice) {
                    case 1 -> System.out.println(student);
                    case 0 -> System.out.println("Returning to login...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 0);
        } else {
            System.out.println("Student not found. Exiting system.");
        }
    }

    private static void viewAllTeachers() {
        System.out.println("\n=== View All Teachers ===");
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    private static void viewAllStudents() {
        System.out.println("\n=== View All Students ===");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void manageStudents(Scanner scanner) {
        System.out.println("\n=== Manage Students ===");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        students.add(new Student(name, id));
        System.out.println("Student added successfully!");
    }

    private static void manageTeachers(Scanner scanner) {
        System.out.println("\n=== Manage Teachers ===");
        System.out.print("Enter teacher name: ");
        String name = scanner.nextLine();
        System.out.print("Enter teacher ID: ");
        String id = scanner.nextLine();
        teachers.add(new Teacher(name, id));
        System.out.println("Teacher added successfully!");
    }

    private static void manageCourses(Scanner scanner) {
        System.out.println("\n=== Manage Courses ===");
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        courses.add(new Course(name, code));
        System.out.println("Course added successfully!");
    }

    private static void assignGrades(Scanner scanner) {
        System.out.println("\n=== Assign Grades ===");
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = findCourseByCode(courseCode);

        if (course == null) {
            System.out.println("Course not found!");
            return;
        }

        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();
        student.addGrade(course, grade);
        System.out.println("Grade assigned successfully!");
    }

    private static void viewGrades() {
        System.out.println("\n=== View Grades ===");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void generateReport() {
        System.out.println("\n=== Generate Report ===");
        try (PrintWriter writer = new PrintWriter(new FileWriter("report.txt"))) {
            for (Student student : students) {
                writer.println(student);
            }
            System.out.println("Report generated successfully! Check the file: report.txt");
        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private static Student findStudentById(String id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    private static Teacher findTeacherById(String id) {
        return teachers.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    private static Course findCourseByCode(String code) {
        return courses.stream().filter(c -> c.getCode().equals(code)).findFirst().orElse(null);
    }
}

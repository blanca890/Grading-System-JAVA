import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();

    private static final Map<String, String> userCredentials = new HashMap<>();
    private static final Map<String, String> userRoles = new HashMap<>();
    private static final Map<String, String> userStudentIdMap = new HashMap<>();

    static {
        // Add credentials and users
        userCredentials.put("admin", "admin123");
        userRoles.put("admin", "Admin");

        userCredentials.put("teacher1", "teacher123");
        userRoles.put("teacher1", "Teacher");

        userCredentials.put("student1", "student123");
        userRoles.put("student1", "Student");
        userStudentIdMap.put("student1", "2024956");

        userCredentials.put("student2", "student456");
        userRoles.put("student2", "Student");
        userStudentIdMap.put("student2", "2024098");

        userCredentials.put("student3", "student789");
        userRoles.put("student3", "Student");
        userStudentIdMap.put("student3", "2024451");

        // Sample courses
        courses.add(new Course("Mathematics", "MATH101"));
        courses.add(new Course("Science", "SCI101"));
        courses.add(new Course("History", "HIST101"));

        // Sample users (dummy accounts)
        students.add(new Student("Arnold Bravo", "2024956"));
        students.add(new Student("Gon Saraza", "2024098"));
        students.add(new Student("Jenny Cruz", "2024451"));

        teachers.add(new Teacher("John Smith", "111111"));
        teachers.add(new Teacher("James Gun", "222222"));
        teachers.add(new Teacher("Jane Doe", "333333"));

        // Assign subjects to teachers
        teachers.get(0).setSubjects(List.of("Mathematics"));
        teachers.get(1).setSubjects(List.of("History"));
        teachers.get(2).setSubjects(List.of("Science"));

        // Assign grades to students
        students.get(0).addGrade(courses.get(0), "A");
        students.get(0).addGrade(courses.get(1), "B");
        students.get(1).addGrade(courses.get(2), "C");
        students.get(2).addGrade(courses.get(0), "B+");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("==========================================");
            System.out.println("======== Welcome to Grading System =======");
            System.out.println("==========================================");
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            ClearScreen();

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

    private static void adminInterface(Scanner scanner) {
        int choice;
        do {
            System.out.println("=============================");
            System.out.println("====== Admin Interface ======");
            System.out.println("=============================");
            System.out.println("1. Teacher");
            System.out.println("2. Students");
            System.out.println("3. Generate Report");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();

            switch (choice) {
                case 1 -> teacherManagement(scanner);  // Navigate Teacher options
                case 2 -> studentManagement(scanner);  // Navigate Student options
                case 3 -> generateReport();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void teacherManagement(Scanner scanner) {
        int choice;
        do {
            System.out.println("=================================");
            System.out.println("====== Teacher Management =======");
            System.out.println("=================================");
            System.out.println("1. View All Teachers");
            System.out.println("2. Add Teacher");
            System.out.println("3. Remove Teacher");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();

            switch (choice) {
                case 1 -> viewAllTeachers();
                case 2 -> addTeacher(scanner);
                case 3 -> removeTeacher(scanner);
                case 0 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void studentManagement(Scanner scanner) {
        int choice;
        do {
            System.out.println("===============================");
            System.out.println("===== Student Management =====");
            System.out.println("===============================");
            System.out.println("1. View All Students with Grades");
            System.out.println("2. Add Student");
            System.out.println("3. Remove Student");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();

            switch (choice) {
                case 1 -> viewAllStudents();
                case 2 -> addStudent(scanner);
                case 3 -> removeStudent(scanner);
                case 0 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void assignGrades(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = findStudentById(studentId);
        if (student != null) {
            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine();
            Course course = findCourseByCode(courseCode);
            if (course != null) {
                assignGrade(scanner, student, course);
            } else {
                System.out.println("Course not found.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void assignGrade(Scanner scanner, Student student, Course course) {
        System.out.print("Enter grade for " + student.getName() + " in " + course.getName() + ": ");
        String grade = scanner.nextLine();
        student.addGrade(course, grade);
        System.out.println("Grade " + grade + " assigned to " + student.getName() + " for " + course.getName());
    }

    private static void viewGrades() {
        System.out.println("===============================");
        System.out.println("=== View All Student Grades ===");
        System.out.println("===============================");
        System.out.printf("%-20s %-10s %-30s%n", "Student Name", "ID", "Grades");
        System.out.println("--------------------------------------------------------------");
        for (Student student : students) {
            System.out.printf("%-20s %-10s %-30s%n", student.getName(), student.getId(), student.getGrades());
        }
    }

    private static Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    private static void addTeacher(Scanner scanner) {
        System.out.print("Enter teacher name: ");
        String name = scanner.nextLine();
        System.out.print("Enter teacher ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter subjects (comma-separated): ");
        String subjects = scanner.nextLine();
        teachers.add(new Teacher(name, id, Arrays.asList(subjects.split(","))));
        System.out.println("Teacher added successfully.");
    }

    private static void removeTeacher(Scanner scanner) {
        System.out.print("Enter teacher ID to remove: ");
        String id = scanner.nextLine();
        Teacher teacher = findTeacherById(id);
        if (teacher != null) {
            teachers.remove(teacher);
            System.out.println("Teacher removed successfully.");
        } else {
            System.out.println("Teacher not found.");
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        students.add(new Student(name, id));
        System.out.println("Student added successfully.");
    }

    private static void removeStudent(Scanner scanner) {
        System.out.print("Enter student ID to remove: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void viewAllTeachers() {
        System.out.println("===============================");
        System.out.println("===== View All Teachers ======");
        System.out.println("===============================");
        System.out.printf("%-20s %-10s %-30s%n", "Teacher Name", "ID", "Subjects");
        System.out.println("--------------------------------------------------------------");
        for (Teacher teacher : teachers) {
            System.out.printf("%-20s %-10s %-30s%n", teacher.getName(), teacher.getId(), teacher.getSubjects());
        }
    }

    public static void viewAllStudents() {
        System.out.println("===============================");
        System.out.println("====== View All Students ======");
        System.out.println("===============================");
        System.out.printf("%-20s %-10s %-20s %-20s %-10s%n", "Student Name", "ID", "Course", "Code", "Grade");
        System.out.println("--------------------------------------------------------------------------------");
        for (Student student : students) {
            boolean firstEntry = true;
            for (Map.Entry<Course, String> entry : student.getGrades().entrySet()) {
                Course course = entry.getKey();
                String grade = entry.getValue();
                if (firstEntry) {
                    System.out.printf("%-20s %-10s %-20s %-20s %-10s%n", student.getName(), student.getId(), course.getName(), course.getCode(), grade);
                    firstEntry = false;
                } else {
                    System.out.printf("%-20s %-10s %-20s %-20s %-10s%n", "", "", course.getName(), course.getCode(), grade);
                }
            }
        }
    }

    private static Teacher findTeacherById(String id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId().equals(id)) {
                return teacher;
            }
        }
        return null;
    }

    private static Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    private static void generateReport() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"))) {
            writer.write("Teachers:\n");
            for (Teacher teacher : teachers) {
                writer.write("Name: " + teacher.getName() + ", ID: " + teacher.getId() + ", Subjects: " + teacher.getSubjects() + "\n");
            }

            writer.write("\nStudents:\n");
            for (Student student : students) {
                writer.write("Name: " + student.getName() + ", ID: " + student.getId() + ", Grades: " + student.getGrades() + "\n");
            }

            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the report.");
            e.printStackTrace();
        }
    }

    public static void teacherInterface(Scanner scanner, String teacherId) {
        int choice;
        do {
            System.out.println("===============================");
            System.out.println("====== Teacher Interface ======");
            System.out.println("===============================");
            System.out.println("1. Assign Grades");
            System.out.println("2. View Grades");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();

            switch (choice) {
                case 1 -> assignGrades(scanner);
                case 2 -> viewGrades();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public static void studentInterface(Scanner scanner, String username) {
        int choice = 0;
        do {
            System.out.println("===============================");
            System.out.println("=======Student Interface=======");
            System.out.println("===============================");
            System.out.println("1. View Grades");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            ClearScreen();

            switch (choice) {
                case 1 -> viewStudentGrades(username);
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void viewStudentGrades(String username) {
        String studentId = userStudentIdMap.get(username);
        Student student = findStudentById(studentId);
        if (student != null) {
            System.out.println("===============================");
            System.out.println("======= Student Grades ========");
            System.out.println("===============================");
            System.out.printf("%-20s %-10s%n", "Course", "Grade");
            System.out.println("----------------------------------------");
            student.getGrades().forEach((course, grade) -> System.out.printf("%-20s %-10s%n", course.getName(), grade));
        } else {
            System.out.println("Student not found.");
        }
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
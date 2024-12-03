import java.io.Console;
import java.util.*;

public class Main {

    private static final List<Student> students = User.getStudents();
    private static final List<Teacher> teachers = User.getTeachers();
    private static final List<Course> courses = User.getCourses();
    private static final Map<String, String> userStudentIdMap = new HashMap<>(); 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();

        while (true) {
            System.out.println("==========================================");
            System.out.println("||         Welcome to Grading System    ||");
            System.out.println("==========================================");
            System.out.println("|| Please log in to continue            ||");
            System.out.println("==========================================");
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            String password;
            if (console == null) {
                System.out.print("Enter Password: ");
                password = scanner.nextLine();
            } else {
                char[] passwordArray = console.readPassword("Enter Password: ");
                password = new String(passwordArray);
            }
            ClearScreen();

            if (User.authenticateUser(username, password)) {
                System.out.print("Welcome, " + username + "! ");
                String role = User.userRoles.get(username);
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

    private static void adminInterface(Scanner scanner) {
        Admin admin = new Admin("admin");
        admin.navigate(scanner);
    }

    private static void teacherManagement(Scanner scanner) {
        Admin.teacherManagement(scanner);
    }

    private static void studentManagement(Scanner scanner) {
        Admin.studentManagement(scanner);
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
        System.out.print("Enter grade for Quiz 1: ");
        int quiz1 = scanner.nextInt();
        System.out.print("Enter grade for Quiz 2: ");
        int quiz2 = scanner.nextInt();
        System.out.print("Enter grade for Quiz 3: ");
        int quiz3 = scanner.nextInt();
        System.out.print("Enter grade for Project: ");
        int project = scanner.nextInt();
        System.out.print("Enter grade for Summative Exam: ");
        int summativeExam = scanner.nextInt();
        System.out.print("Enter grade for Final Exam: ");
        int finalExam = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        double totalGrade = (quiz1 + quiz2 + quiz3 + project + summativeExam + finalExam) / 6.0;
        String status = totalGrade >= 60 ? "Pass" : "Fail";
        student.addGrade(course, quiz1, quiz2, quiz3, project, summativeExam, finalExam, totalGrade, status);
        System.out.println("Grades assigned to " + student.getName() + " for " + course.getName());
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

    private static void viewGrades() {
        Student.viewAllStudents(students);
    }



    private static void viewStudentGrades(String username) {
        String studentId = userStudentIdMap.get(username);
        Student student = Student.findStudentById(students, studentId);
        if (student != null) {
            student.viewGrades();
        } else {
            System.out.println("Student not found.");
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

    public static void viewAllStudents() {
        ClearScreen();
        System.out.println("\n ");
        System.out.println("\n==============================================================================================================================================================");
        System.out.println("========================================================== VIEW ALL STUDENTS WITH GRADES =====================================================================");
        System.out.println("==============================================================================================================================================================");
        System.out.printf("%-20s | %-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s%n", "Student Name", "ID", "Course", "Quiz 1", "Quiz 2", "Quiz 3", "Project", "Summative Exam", "Final Exam", "Percentage", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Student student : students) {
            boolean firstEntry = true;
            if (student.getGrades().isEmpty()) {
                // If the student has no grades, still print the student info
                System.out.printf("%-20s | %-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s%n", student.getName(), student.getId(), "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
            } else {
                for (Map.Entry<Course, Map<String, Integer>> entry : student.getGrades().entrySet()) {
                    Course course = entry.getKey();
                    Map<String, Integer> gradeDetails = entry.getValue();
                    int total = gradeDetails.get("Quiz 1") + gradeDetails.get("Quiz 2") + gradeDetails.get("Quiz 3") + gradeDetails.get("Project") + gradeDetails.get("Summative Exam") + gradeDetails.get("Final Exam");
                    double percentage = total / 6.0;
                    String status = percentage >= 75 ? "Pass" : "Fail";
                    if (firstEntry) {
                        System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", student.getName(), student.getId(), course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
                        firstEntry = false;
                    } else {
                        System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", "", "", course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
                    }
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                
            }
        }
    }

    private static Student findStudentById(String studentId) {
        return Student.findStudentById(students, studentId);
    }

    public static void generateReport() {
        Admin.generateReport();
    }

    public static void teacherInterface(Scanner scanner, String teacherId) {
        Teacher.teacherInterface(scanner, teacherId);
    }

    public static void studentInterface(Scanner scanner, String username) {
        Student.studentInterface(scanner, username);
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static List<Teacher> getTeachers() {
        return teachers;
    }

    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
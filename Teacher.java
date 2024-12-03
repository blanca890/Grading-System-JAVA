import java.io.*;
import java.util.*;

public class Teacher extends User {

    private String name;
    private String id;
    private List<String> subjects;

    public Teacher(String name, String id) {
        super(id); // Call to the superclass constructor
        this.name = name;
        this.id = id;
        this.subjects = new ArrayList<>();
    }

    public Teacher(String name, String id, List<String> subjects) {
        super(id); // Call to the superclass constructor
        this.name = name;
        this.id = id;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    // Method to assign grade to a student
    public void assignGrade(Scanner scanner, Student student, Course course) {
        System.out.print("Enter grade for Quiz 1 (1-100): ");
        int quiz1 = scanner.nextInt();
        System.out.print("Enter grade for Quiz 2 (1-100): ");
        int quiz2 = scanner.nextInt();
        System.out.print("Enter grade for Quiz 3 (1-100): ");
        int quiz3 = scanner.nextInt();
        System.out.print("Enter grade for Project (1-100): ");
        int project = scanner.nextInt();
        System.out.print("Enter grade for Summative Exam (1-100): ");
        int summativeExam = scanner.nextInt();
        System.out.print("Enter grade for Final Exam (1-100): ");
        int finalExam = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        int total = quiz1 + quiz2 + quiz3 + project + summativeExam + finalExam;
        double percentage = total / 6.0;
        String status = percentage >= 75 ? "Pass" : "Fail";

        student.addGrade(course, quiz1, quiz2, quiz3, project, summativeExam, finalExam, percentage, status);
        System.out.println("Grades assigned to " + student.getName() + " for " + course.getName());
    }

    public void assignGradeToStudent(Scanner scanner) {
        System.out.print("Enter student ID to assign grade to: ");
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

    public void viewGrades() {
        System.out.println("\n==============================================================================================================================================================");
        System.out.println("========================================================== VIEW ALL STUDENTS WITH GRADES =======================================================================");
        System.out.println("==============================================================================================================================================================");
        System.out.printf("%-20s | %-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s%n", "Student Name", "ID", "Course", "Quiz 1", "Quiz 2", "Quiz 3", "Project", "Summative Exam", "Final Exam", "Percentage", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Student student : Main.getStudents()) {
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

    public static void viewAllTeachers() {
        System.out.println("===============================");
        System.out.println("===== View All Teachers ======");
        System.out.println("===============================");
        System.out.printf("%-20s %-10s %-30s%n", "Teacher Name", "ID", "Subjects");
        System.out.println("--------------------------------------------------------------");
        for (Teacher teacher : Main.getTeachers()) {
            System.out.printf("%-20s %-10s %-30s%n", teacher.getName(), teacher.getId(), teacher.getSubjects());
        }
    }

    public static void addTeacher(Scanner scanner) {
        System.out.print("Enter teacher name: ");
        String name = scanner.nextLine();
        System.out.print("Enter teacher ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter subjects (comma-separated): ");
        String subjects = scanner.nextLine();
        Main.getTeachers().add(new Teacher(name, id, Arrays.asList(subjects.split(","))));
        System.out.println("Teacher added successfully.");
    }

    public static void removeTeacher(Scanner scanner) {
        System.out.print("Enter teacher ID to remove: ");
        String id = scanner.nextLine();
        Teacher teacher = findTeacherById(id);
        if (teacher != null) {
            Main.getTeachers().remove(teacher);
            System.out.println("Teacher removed successfully.");
        } else {
            System.out.println("Teacher not found.");
        }
    }

    private static Teacher findTeacherById(String id) {
        for (Teacher teacher : Main.getTeachers()) {
            if (teacher.getId().equals(id)) {
                return teacher;
            }
        }
        return null;
    }

    public static void teacherInterface(Scanner scanner, String teacherId) {
        int choice;
        do {
            System.out.println("\n=== TEACHER INTERFACE ===");
            System.out.println("[ 1 ] - VIEW ALL STUDENTS");
            System.out.println("[ 2 ] - UPDATE STUDENT GRADES");
            System.out.println("[ 3 ] - SAVE");
            System.out.println("[ 0 ] - LOGOUT");
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
                case 1 -> Student.viewAllStudents(Main.getStudents());
                case 2 -> new Teacher("", "").assignGradeToStudent(scanner);
                case 3 -> saveGradesToFile();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void saveGradesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("grades.txt"))) {
            for (Student student : Main.getStudents()) {
                writer.write("Grades for " + student.getName() + " (" + student.getId() + "):\n");
                writer.write(student.getCourseGradeTable());
                writer.write("\n");
            }
            System.out.println("Grades saved to grades.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the grades.");
            e.printStackTrace();
        }
    }

    @Override
    public void navigate(Scanner scanner) {
        System.out.println("\nWelcome Teacher!");
        int choice;
        do {
            System.out.println("\n===============================");
            System.out.println("========= TEACHER MENU =========");
            System.out.println("===============================");
            System.out.println("[ 1 ] - ASSIGN GRADES");
            System.out.println("[ 2 ] - VIEW GRADES");
            System.out.println("[ 0 ] - LOGOUT");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline after nextInt()

            switch (choice) {
                case 1:
                    assignGradeToStudent(scanner);
                    break;
                case 2:
                    System.out.println("Viewing grades...");
                    // Logic to view grades should be added here
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    @Override
    public String toString() {
        return "Teacher Name: " + name + ", ID: " + id;
    }

    private static Student findStudentById(String studentId) {
        for (Student student : Main.getStudents()) {
            if (student.getId().equals(studentId)) {                return student;            }        }
        return null;
    }

    private static Course findCourseByCode(String courseCode) {
        for (Course course : Main.getCourses()) {
            if (course.getCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}

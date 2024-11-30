import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    @Override
    public void navigate(Scanner scanner) {
        System.out.println("\nWelcome Teacher!");
        int choice;
        do {
            System.out.println("\n=== Teacher Menu ===");
            System.out.println("1. Assign Grades");
            System.out.println("2. View Grades");
            System.out.println("0. Logout");
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
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
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

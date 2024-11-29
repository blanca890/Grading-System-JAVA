import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Student extends User {

    private String name;
    private String id;
    
    private Map<Course, String> grades = new HashMap<>();

    public Student(String name, String id) {
        super(id); // Call to the superclass constructor
        this.name = name;
        this.id = id;
    }

    public Map<Course, String> getGrades() {

        return grades;

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    // Method to add grades to a student
    public void addGrade(Course course, String grade) {
        grades.put(course, grade);
    }

    @Override
    public void navigate(Scanner scanner) {
        System.out.println("\nWelcome Student!");
        int choice;
        do {
            System.out.println("\n=== Student Menu ===");
            System.out.println("1. View Grades");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline after nextInt()

            switch (choice) {
                case 1:
                    System.out.println("Viewing Grades...");
                    grades.forEach((course, grade) -> System.out.println(course.getName() + ": " + grade));
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
        return "Student Name: " + name + ", ID: " + id + ", Grades: " + grades;
    }
}

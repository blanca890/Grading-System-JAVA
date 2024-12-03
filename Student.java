import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Student {

    private String name;
    private String id;
    private Map<Course, Map<String, Integer>> grades;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.grades = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void addGrade(Course course, int quiz1, int quiz2, int quiz3, int project, int summativeExam, int finalExam, double percentage, String status) {
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Quiz 1", quiz1);
        grades.put("Quiz 2", quiz2);
        grades.put("Quiz 3", quiz3);
        grades.put("Project", project);
        grades.put("Summative Exam", summativeExam);
        grades.put("Final Exam", finalExam);
        grades.put("Total", (int) percentage);
        grades.put("Status", status.equals("Pass") ? 1 : 0);
        this.grades.put(course, grades);
    }

    public Map<Course, Map<String, Integer>> getGrades() {
        return grades;
    }

    public String getCourseGradeTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s %-10s %-10s %-10s %-15s %-10s %-10s %-10s%n", "Course", "Quiz 1", "Quiz 2", "Quiz 3", "Project", "Summative Exam", "Final Exam", "Percentage", "Status"));
        sb.append("--------------------------------------------------------------------------------------------------------\n");
        for (Map.Entry<Course, Map<String, Integer>> entry : grades.entrySet()) {
            Course course = entry.getKey();
            Map<String, Integer> gradeDetails = entry.getValue();
            int total = gradeDetails.get("Quiz 1") + gradeDetails.get("Quiz 2") + gradeDetails.get("Quiz 3") + gradeDetails.get("Project") + gradeDetails.get("Summative Exam") + gradeDetails.get("Final Exam");
            double percentage = total / 6.0;
            String status = percentage >= 75 ? "Pass" : "Fail";
            sb.append(String.format("%-20s %-10d %-10d %-10d %-10d %-15d %-10d %-10.2f %-10s%n", course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status));
        }
        return sb.toString();
    }

    public void viewGrades() {
        clearScreen();
        System.out.println("\n==============================================================================================================================================================");
        System.out.println("================================================================== STUDENT GRADES ============================================================================");
        System.out.println("==============================================================================================================================================================");
        System.out.printf("%-20s | %-10s | %-20s | %-10s | %-10s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s%n", "Student Name", "ID", "Course", "Quiz 1", "Quiz 2", "Quiz 3", "Project", "Summative Exam", "Final Exam", "Percentage", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        boolean firstEntry = true;
        for (Map.Entry<Course, Map<String, Integer>> entry : this.grades.entrySet()) {
            Course course = entry.getKey();
            Map<String, Integer> gradeDetails = entry.getValue();
            int total = gradeDetails.get("Quiz 1") + gradeDetails.get("Quiz 2") + gradeDetails.get("Quiz 3") + gradeDetails.get("Project") + gradeDetails.get("Summative Exam") + gradeDetails.get("Final Exam");
            double percentage = total / 6.0;
            String status = percentage >= 75 ? "Pass" : "Fail";
            if (firstEntry) {
                System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", this.name, this.id, course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
                firstEntry = false;
            } else {
                System.out.printf("%-20s | %-10s | %-20s | %-10d | %-10d | %-10d | %-10d | %-15d | %-10d | %-10.2f | %-10s%n", "", "", course.getName(), gradeDetails.get("Quiz 1"), gradeDetails.get("Quiz 2"), gradeDetails.get("Quiz 3"), gradeDetails.get("Project"), gradeDetails.get("Summative Exam"), gradeDetails.get("Final Exam"), percentage, status);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            
        }
    }

    public void printGrades() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.name + "_grades.txt"))) {
            writer.write("Grades for " + this.name + " (" + this.id + "):\n");
            writer.write(getCourseGradeTable());
            System.out.println("Grades saved to " + this.name + "_grades.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the grades.");
            e.printStackTrace();
        }
    }

    public static void viewAllStudents(List<Student> students) {
        System.out.println("\n==============================================================================================================================================================");
        System.out.println("========================================================== VIEW ALL STUDENTS WITH GRADES =======================================================================");
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

    public static Student findStudentById(List<Student> students, String studentId) {
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void studentInterface(Scanner scanner, String username) {
        Student student = findStudentById(User.getStudents(), User.userStudentIdMap.get(username));
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        int choice;
        do {
            System.out.println("\n=== STUDENT INTERFACE ===");
            System.out.println("[ 1 ] - VIEW MY GRADES");
            System.out.println("[ 2 ] - PRINT MY GRADE");
            System.out.println("[ 0 ] - LOGOUT");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            clearScreen();

            switch (choice) {
                case 1 -> student.viewGrades();
                case 2 -> student.printGrades();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
import java.util.HashMap;
import java.util.Map;

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
}
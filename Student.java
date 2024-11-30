import java.util.HashMap;
import java.util.Map;

public class Student {

    private String name;
    private String id;
    private Map<Course, String> grades;

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

    public void addGrade(Course course, String grade) {
        grades.put(course, grade);
    }

    public Map<Course, String> getGrades() {
        return grades;
    }

    public String getCourseGradeTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s %-10s%n", "Course", "Code", "Grade"));
        sb.append("--------------------------------------------\n");
        for (Map.Entry<Course, String> entry : grades.entrySet()) {
            Course course = entry.getKey();
            String grade = entry.getValue();
            sb.append(String.format("%-20s %-10s %-10s%n", course.getName(), course.getCode(), grade));
        }
        return sb.toString();
    }
}
import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private String id;
    private final Map<Course, String> grades = new HashMap<>();

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
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

    @Override
    public String toString() {
        StringBuilder gradesString = new StringBuilder();
        for (Map.Entry<Course, String> entry : grades.entrySet()) {
            gradesString.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append(", ");
        }
        if (gradesString.length() > 0) gradesString.setLength(gradesString.length() - 2);
        return "Student Name: " + name + ", ID: " + id + ", Grades: {" + gradesString + "}";
    }
}

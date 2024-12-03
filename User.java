import java.util.*;

public abstract class User {
    private String id;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract void navigate(Scanner scanner);

    protected static final Map<String, String> userCredentials = new HashMap<>();
    protected static final Map<String, String> userRoles = new HashMap<>();
    protected static final Map<String, String> userStudentIdMap = new HashMap<>();
    protected static final List<Student> students = new ArrayList<>();
    protected static final List<Teacher> teachers = new ArrayList<>();
    protected static final List<Course> courses = new ArrayList<>();

    static {
        // Add credentials and users
        userCredentials.put("admin", "admin1");
        userRoles.put("admin", "Admin");

        userCredentials.put("teacher1", "teacher1");
        userRoles.put("teacher1", "Teacher");

        userCredentials.put("student1", "student1");
        userRoles.put("student1", "Student");
        userStudentIdMap.put("student1", "2024956");

        userCredentials.put("student2", "student2");
        userRoles.put("student2", "Student");
        userStudentIdMap.put("student2", "2024098");

        userCredentials.put("student3", "student3");
        userRoles.put("student3", "Student");
        userStudentIdMap.put("student3", "2024451");

        userCredentials.put("student4", "student4");
        userRoles.put("student4", "Student");
        userStudentIdMap.put("student4", "2024000");

        // Sample courses
        courses.add(new Course("Mathematics", "MATH101"));
        courses.add(new Course("Science", "SCI101"));
        courses.add(new Course("History", "HIST101"));
        courses.add(new Course("English", "ENG101"));
        courses.add(new Course("Computer Science", "CS101"));
        courses.add(new Course("Physics", "PHYS101"));
        courses.add(new Course("Chemistry", "CHEM101"));
        courses.add(new Course("Biology", "BIO101"));
        courses.add(new Course("Art", "ART101"));
        courses.add(new Course("Physical Education", "PE101"));

        // Sample users (dummy accounts)
        students.add(new Student("Arnold Bravo", "2024956"));
        students.add(new Student("Gon Saraza", "2024098"));
        students.add(new Student("Jenny Cruz", "2024451"));
        students.add(new Student("Alice Johnson", "2025001"));
        students.add(new Student("Bob Brown", "2025002"));

        teachers.add(new Teacher("John Smith", "111111"));
        teachers.add(new Teacher("James Gun", "222222"));
        teachers.add(new Teacher("Jane Doe", "333333"));
        teachers.add(new Teacher("Emily Davis", "444444"));
        teachers.add(new Teacher("Michael Scott", "555555"));
        teachers.add(new Teacher("Sarah Connor", "666666"));
        teachers.add(new Teacher("Bruce Wayne", "777777"));
        teachers.add(new Teacher("Clark Kent", "888888"));
        teachers.add(new Teacher("Diana Prince", "999999"));
        teachers.add(new Teacher("Barry Allen", "101010"));

        // Assign subjects to teachers
        teachers.get(0).setSubjects(List.of("Mathematics"));
        teachers.get(1).setSubjects(List.of("History"));
        teachers.get(2).setSubjects(List.of("Science"));
        teachers.get(3).setSubjects(List.of("English"));
        teachers.get(4).setSubjects(List.of("Computer Science"));
        teachers.get(5).setSubjects(List.of("Physics"));
        teachers.get(6).setSubjects(List.of("Chemistry"));
        teachers.get(7).setSubjects(List.of("Biology"));
        teachers.get(8).setSubjects(List.of("Art"));
        teachers.get(9).setSubjects(List.of("Physical Education"));

        // Assign grades to students
        students.get(0).addGrade(courses.get(0), 90, 90, 90, 90, 90, 90, 90.0, "Pass");
        students.get(0).addGrade(courses.get(1), 80, 80, 80, 80, 80, 80, 80.0, "Pass");
        students.get(0).addGrade(courses.get(5), 85, 85, 85, 85, 85, 85, 85.0, "Pass");

        students.get(1).addGrade(courses.get(2), 70, 70, 70, 70, 70, 70, 70.0, "Fail");
        students.get(1).addGrade(courses.get(6), 75, 75, 75, 75, 75, 75, 75.0, "Pass");
        students.get(1).addGrade(courses.get(7), 65, 65, 65, 65, 65, 65, 65.0, "Fail");

        students.get(2).addGrade(courses.get(0), 85, 85, 85, 85, 85, 85, 85.0, "Pass");
        students.get(2).addGrade(courses.get(3), 90, 90, 90, 90, 90, 90, 90.0, "Pass");
        students.get(2).addGrade(courses.get(8), 95, 95, 95, 95, 95, 95, 95.0, "Pass");

        students.get(3).addGrade(courses.get(3), 95, 95, 95, 95, 95, 95, 95.0, "Pass");
        students.get(3).addGrade(courses.get(4), 88, 88, 88, 88, 88, 88, 88.0, "Pass");
        students.get(3).addGrade(courses.get(5), 92, 92, 92, 92, 92, 92, 92.0, "Pass");

        students.get(4).addGrade(courses.get(0), 60, 60, 60, 60, 60, 60, 60.0, "Pass");
        students.get(4).addGrade(courses.get(2), 75, 75, 75, 75, 75, 75, 75.0, "Pass");
        students.get(4).addGrade(courses.get(6), 70, 70, 70, 70, 70, 70, 70.0, "Pass");
    }

    protected void clearScreen() {
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

    public static List<Student> getStudents() {
        return students;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static List<Teacher> getTeachers() {
        return teachers;
    }

    public static boolean authenticateUser(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    public static void viewStudentGrades(String username, List<Student> students) {
        String studentId = userStudentIdMap.get(username);
        Student student = Student.findStudentById(students, studentId);
        if (student != null) {
            student.viewGrades();
        } else {
            System.out.println("Student not found.");
        }
    }
}


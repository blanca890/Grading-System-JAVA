public class Teacher {
    private String name;
    private String id;

    public Teacher(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Teacher Name: " + name + ", ID: " + id;
    }
}

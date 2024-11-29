import java.util.Scanner;

public abstract class User {
    private String id;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract void navigate(Scanner scanner);
}


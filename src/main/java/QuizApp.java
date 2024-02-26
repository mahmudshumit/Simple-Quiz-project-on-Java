import java.util.List;

public class QuizApp {
    public static void main(String[] args) {
        List<User> users = JsonParser.loadUsers("users.json");

        // Add admin and student users to the users.json file
        users.add(new User("admin", "admin123", "admin"));
        users.add(new User("salman", "student123", "student"));

        JsonParser.saveUsers("users.json", users);
    }
}

import java.util.List;
import java.util.Scanner;

public class QuizSystem {
    private static final String USER_FILE = "./src/main/resources/users.json";
    private static final String QUIZ_FILE = "./src/main/resources/quiz.json";

    private User currentUser;
    private QuizBank quizBank;

    public QuizSystem() {
        this.quizBank = new QuizBank();
    }

    public void start() {
        System.out.println("System:> Enter your username");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("System:> Enter password");
        String password = scanner.nextLine();

        List<User> users = JsonParser.loadUsers(USER_FILE);
        currentUser = authenticateUser(username, password, users);

        if (currentUser != null) {
            if ("admin".equals(currentUser.getRole())) {
                adminMenu();
            } else if ("student".equals(currentUser.getRole())) {
                studentMenu();
            }
        } else {
            System.out.println("System:> Invalid username or password. Exiting...");
        }
    }

    private User authenticateUser(String username, String password, List<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private void adminMenu() {
        System.out.println("System:> Welcome " + currentUser.getUsername() + "! Please create new questions in the question bank.");

        while (true) {
            addQuestion();
            System.out.println("System:> Do you want to add more questions? (press 's' for start and 'q' for quit)");
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equals("q")) {
                break;
            }
        }

        JsonParser.saveQuestions(QUIZ_FILE, quizBank.getQuestions());
    }

    private void addQuestion() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("System:> Input your question");
        String question = scanner.nextLine();

        System.out.println("Admin:> Input option 1:");
        String option1 = scanner.nextLine();

        System.out.println("Admin:> Input option 2:");
        String option2 = scanner.nextLine();

        System.out.println("Admin:> Input option 3:");
        String option3 = scanner.nextLine();

        System.out.println("Admin:> Input option 4:");
        String option4 = scanner.nextLine();

        System.out.println("System:> What is the answer key?");
        int answerKey = Integer.parseInt(scanner.nextLine());

        Question newQuestion = new Question(question, option1, option2, option3, option4, answerKey);
        quizBank.addQuestion(newQuestion);
        System.out.println("System:> Saved successfully!");
    }

    private void studentMenu() {
        System.out.println("System:> Welcome " + currentUser.getUsername() + " to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("s")) {
            quizBank.loadQuestions();  // Ensure questions are loaded before conducting the quiz
            List<Question> randomQuestions = quizBank.getRandomQuestions(10);
            int score = conductQuiz(randomQuestions);
            displayResult(score);
        }
    }


    private int conductQuiz(List<Question> questions) {

        int score = 0;
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < questions.size(); i++) {
            Question currentQuestion = questions.get(i);
            System.out.println("[Question " + (i + 1) + "] " + currentQuestion.getQuestion());
            System.out.println("1. " + currentQuestion.getOption1());
            System.out.println("2. " + currentQuestion.getOption2());
            System.out.println("3. " + currentQuestion.getOption3());
            System.out.println("4. " + currentQuestion.getOption4());

            int userAnswer;
            while (true) {
                try {
                    System.out.print("Your answer: ");
                    userAnswer = Integer.parseInt(scanner.nextLine());
                    if (userAnswer >= 1 && userAnswer <= 4) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                }
            }

            if (userAnswer == currentQuestion.getAnswerKey()) {
                score++;
            }
        }

        return score;
    }
    private void displayResult(int score) {
        System.out.println("Quiz completed! Your score: " + score + " out of 10");

        if (score >= 8) {
            System.out.println("Excellent! You have got " + score + " out of 10");
        } else if (score >= 5) {
            System.out.println("Good. You have got " + score + " out of 10");
        } else if (score >= 2) {
            System.out.println("Very poor! You have got " + score + " out of 10");
        } else {
            System.out.println("Very sorry you are failed. You have got " + score + " out of 10");
        }

        handleUserInputAfterQuiz();
    }

    private void handleUserInputAfterQuiz() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Would you like to start again? Press 's' for start or 'q' for quit.");
            String userInput = scanner.nextLine().trim().toLowerCase();

            if (userInput.equals("s")) {
                studentMenu();  // Start the quiz again
            } else if (userInput.equals("q")) {
                System.out.println("Exiting...");
                System.exit(0);  // Quit the program
            } else {
                System.out.println("Invalid input. Please enter 's' for start or 'q' for quit.");
            }
        }
    }

    public static void main(String[] args) {
        QuizSystem quizSystem = new QuizSystem();
        quizSystem.start();
    }
}

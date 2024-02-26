import java.util.List;
import java.util.Scanner;

public class Student {
    private QuizBank quizBank;

    public Student(QuizBank quizBank) {
        this.quizBank = quizBank;
    }

    public void takeQuiz() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("System:> Welcome to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.");

        String startCommand = scanner.nextLine();

        if ("s".equalsIgnoreCase(startCommand)) {
            List<Question> randomQuestions = quizBank.getRandomQuestions(10);

            int score = 0;

            for (int i = 0; i < randomQuestions.size(); i++) {
                Question q = randomQuestions.get(i);

                System.out.println("[Question " + (i + 1) + "] " + q.getQuestion());
                System.out.println("1. " + q.getOption1());
                System.out.println("2. " + q.getOption2());
                System.out.println("3. " + q.getOption3());
                System.out.println("4. " + q.getOption4());

                System.out.print("Your answer: ");
                int userAnswer;

                try {
                    userAnswer = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    i--; // Decrement the loop variable to repeat the current question
                    continue;
                }

                if (userAnswer == q.getAnswerKey()) {
                    score++;
                }
            }

            // Display result
            System.out.println("Quiz completed! Your score: " + score + " out of 10");

            // Display message based on the score
            if (score >= 8) {
                System.out.println("Excellent! You have got " + score + " out of 10");
            } else if (score >= 5) {
                System.out.println("Good. You have got " + score + " out of 10");
            } else if (score >= 2) {
                System.out.println("Very poor! You have got " + score + " out of 10. You need to improve.");
            } else {
                System.out.println("Very sorry, you have failed. Better luck next time.");
            }

            System.out.println("Would you like to start again? Press 's' for start or 'q' for quit.");
        } else {
            System.out.println("Invalid command. Please press 's' to start the quiz.");
        }
    }
}

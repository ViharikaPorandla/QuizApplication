import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizGame {
    private List<QuizQuestion> questions;
    private int score;
    private static final int TIME_LIMIT_SECONDS = 10;

    public QuizGame() {
        questions = new ArrayList<>();
        score = 0;

        // Add questions
        questions.add(new QuizQuestion(
                "What is the capital of France?",
                new String[]{"1. Paris", "2. London", "3. Rome", "4. Berlin"},
                0
        ));
        questions.add(new QuizQuestion(
                "What is 2 + 2?",
                new String[]{"1. 3", "2. 4", "3. 5", "4. 6"},
                1
        ));
        // Add more questions as needed
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion question = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + question.getQuestion());

            for (String option : question.getOptions()) {
                System.out.println(option);
            }

            // Start the timer
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up!");
                }
            };
            timer.schedule(timerTask, TIME_LIMIT_SECONDS * 1000);

            System.out.print("Select your answer (1-4): ");
            int userAnswer = -1;
            try {
                userAnswer = scanner.nextInt() - 1;
            } catch (Exception e) {
                System.out.println("Invalid input. Defaulting to incorrect answer.");
            }

            timer.cancel();  // Cancel the timer if the user has answered

            if (userAnswer >= 0 && userAnswer < 4) {
                if (question.isAnswerCorrect(userAnswer)) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer was: " + (question.getCorrectAnswerIndex() + 1));
                }
            } else {
                System.out.println("Invalid answer. No points awarded.");
            }
        }

        displayResults();
    }

    private void displayResults() {
        System.out.println("\nQuiz Finished!");
        System.out.println("Your final score is: " + score + "/" + questions.size());
    }

    public static void main(String[] args) {
        QuizGame game = new QuizGame();
        game.start();
    }
}

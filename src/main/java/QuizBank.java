import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizBank {
    private List<Question> questions;

    public QuizBank() {
        this.questions = new ArrayList<>();
        loadQuestions(); // Load questions when initializing QuizBank
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
        saveQuestions(); // Save questions after adding a new one
    }

    public List<Question> getRandomQuestions(int count) {
        if (count >= questions.size()) {
            return new ArrayList<>(questions);
        } else {
            List<Question> copy = new ArrayList<>(questions);
            Collections.shuffle(copy);
            return copy.subList(0, count);
        }
    }

    void loadQuestions() {
        String fileName = "./src/main/resources/quiz.json";

        try (FileReader reader = new FileReader(fileName)) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            questions.clear(); // Clear existing questions before loading

            for (Object obj : jsonArray) {
                JSONObject jsonObj = (JSONObject) obj;
                String questionText = (String) jsonObj.get("question");
                String option1 = (String) jsonObj.get("option1");
                String option2 = (String) jsonObj.get("option2");
                String option3 = (String) jsonObj.get("option3");
                String option4 = (String) jsonObj.get("option4");
                long answerKey = (long) jsonObj.get("answerKey");

                Question question = new Question(questionText, option1, option2, option3, option4, (int) answerKey);
                questions.add(question);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void saveQuestions() {
        String fileName = "./src/main/resources/quiz.json";
        JSONArray jsonArray = new JSONArray();

        for (Question question : questions) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("question", question.getQuestion());
            jsonObj.put("option1", question.getOption1());
            jsonObj.put("option2", question.getOption2());
            jsonObj.put("option3", question.getOption3());
            jsonObj.put("option4", question.getOption4());
            jsonObj.put("answerKey", question.getAnswerKey());

            jsonArray.add(jsonObj);
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

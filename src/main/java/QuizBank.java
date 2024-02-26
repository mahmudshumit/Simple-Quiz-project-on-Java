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

class QuizBank {
    private List<Question> questions;


    public QuizBank() {
        this.questions = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
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

    public void loadQuestions() {
        String fileName = "./src/main/resources/quiz.json";

        try (FileReader reader = new FileReader(fileName)) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

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

    public void saveQuestions() {
        String fileName = "./src/main/resources/quiz.json";
        List<Map<String, Object>> questionsData = new ArrayList<>();
        for (Question question : questions) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", question.getQuestion());
            questionData.put("option1", question.getOption1());
            questionData.put("option2", question.getOption2());
            questionData.put("option3", question.getOption3());
            questionData.put("option4", question.getOption4());
            questionData.put("answerKey", question.getAnswerKey());
            questionsData.add(questionData);
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(toJson(questionsData));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, Object>> parseJson(String json) {
        // Implement your JSON parsing logic here
        List<Map<String, Object>> result = new ArrayList<>();
        // Parse your JSON content and populate result
        return result;
    }

    private String toJson(List<Map<String, Object>> data) {
        StringBuilder json = new StringBuilder("[\n");
        for (Map<String, Object> item : data) {
            json.append("{\n");
            for (Map.Entry<String, Object> entry : item.entrySet()) {
                json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",\n");
            }
            json.append("},\n");
        }
        if (!data.isEmpty()) {
            json.delete(json.length() - 2, json.length());
        }
        json.append("]\n");
        return json.toString();
    }
}

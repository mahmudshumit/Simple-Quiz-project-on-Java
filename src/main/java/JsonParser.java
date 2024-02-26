import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    private static final String USERS_FILE_PATH = "./src/main/resources/users.json";
    private static final String QUIZ_FILE_PATH = "./src/main/resources/quiz.json";

    public static List<User> loadUsers(String s) {
        List<User> userList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(USERS_FILE_PATH)) {
            Object obj = jsonParser.parse(reader);
            JSONArray userArray = (JSONArray) obj;

            for (Object userObj : userArray) {
                JSONObject userJson = (JSONObject) userObj;
                String username = (String) userJson.get("username");
                String password = (String) userJson.get("password");
                String role = (String) userJson.get("role");

                userList.add(new User(username, password, role));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static void saveUsers(String s, List<User> users) {
        JSONArray userArray = new JSONArray();

        for (User user : users) {
            JSONObject userJson = new JSONObject();
            userJson.put("username", user.getUsername());
            userJson.put("password", user.getPassword());
            userJson.put("role", user.getRole());

            userArray.add(userJson);
        }

        try (FileWriter writer = new FileWriter(USERS_FILE_PATH)) {
            writer.write(userArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Question> loadQuestions() {
        List<Question> questionList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(QUIZ_FILE_PATH)) {
            Object obj = jsonParser.parse(reader);
            JSONArray questionArray = (JSONArray) obj;

            for (Object questionObj : questionArray) {
                JSONObject questionJson = (JSONObject) questionObj;
                String question = (String) questionJson.get("question");
                String option1 = (String) questionJson.get("option1");
                String option2 = (String) questionJson.get("option2");
                String option3 = (String) questionJson.get("option3");
                String option4 = (String) questionJson.get("option4");
                long answerKey = (long) questionJson.get("answerKey");

                questionList.add(new Question(question, option1, option2, option3, option4, (int) answerKey));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return questionList;
    }

    public static void saveQuestions(String quizFile, List<Question> questions) {
        JSONArray questionArray = new JSONArray();

        for (Question question : questions) {
            JSONObject questionJson = new JSONObject();
            questionJson.put("question", question.getQuestion());
            questionJson.put("option1", question.getOption1());
            questionJson.put("option2", question.getOption2());
            questionJson.put("option3", question.getOption3());
            questionJson.put("option4", question.getOption4());
            questionJson.put("answerKey", question.getAnswerKey());

            questionArray.add(questionJson);
        }

        try (FileWriter writer = new FileWriter(QUIZ_FILE_PATH)) {
            writer.write(questionArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

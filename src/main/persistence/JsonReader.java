package persistence;

import model.Habit;
import model.HabitList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads habitList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public HabitList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHabitList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses habitList from JSON object and returns it
    private HabitList parseHabitList(JSONObject jsonObject) {
        HabitList habitList = new HabitList();

        addHabits(habitList, jsonObject);
        return habitList;
    }

    // MODIFIES: habitList
    // EFFECTS: parses habits from JSON object and adds them to habitList
    private void addHabits(HabitList habitList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("habits");
        for (Object json : jsonArray) {
            JSONObject nextHabit = (JSONObject) json;
            addHabit(habitList, nextHabit);
        }
    }

    // MODIFIES: habitList
    // EFFECTS: parses habit from JSON object and adds it to habitList
    private void addHabit(HabitList habitList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        boolean completed = jsonObject.getBoolean("completed");
        String lastCompletedDate = jsonObject.getString("lastCompletedDate");
        int streak = jsonObject.getInt("streak");

        Habit habit = new Habit(name);
        habit.setCompleted(completed);
        habit.setLastCompletedDate(lastCompletedDate);
        habit.setStreak(streak);

        habitList.addHabit(habit);
    }
}

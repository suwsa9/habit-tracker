package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a List of Habits
public class HabitList implements Writable {
    private List<Habit> habits;

    public HabitList() {
        habits = new ArrayList<>();
    }

    // EFFECTS: returns habits in the list
    public List<Habit> getHabits() {
        return habits;
    }

    // MODIFIES: this
    // EFFECTS: adds a habit to the list of habits
    public void addHabit(Habit habit) {
        habits.add(habit);
        EventLog.getInstance().logEvent(new Event("Habit: " + habit.getName() + " was added."));
    }

    // MODIFIES: this
    // EFFECTS: removes a habit from the list of habits
    public void removeHabit(String name) {
        habits.removeIf(habit -> habit.getName().equals(name));
        EventLog.getInstance().logEvent(new Event("Habit: " + name + " was removed from the list."));
    }

    // MODIFIES: this
    // EFFECTS: marks selected habit as completed
    public void markHabitAsCompleted(String name) {
        for (Habit habit : habits) {
            if (habit.getName().equals(name)) {
                habit.markCompleted();
                return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: marks selected habit as skipped
    public void markHabitAsSkipped(String name) {
        for (Habit habit : habits) {
            if (habit.getName().equals(name)) {
                habit.markSkipped();
                return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: marks selected habit as incomplete
    public void markHabitAsIncomplete(String name) {
        for (Habit habit : habits) {
            if (habit.getName().equals(name)) {
                habit.markIncomplete();
                return;
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("habits", habitsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as JSON array
    private JSONArray habitsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Habit habit : habits) {
            jsonArray.put(habit.toJson());
        }

        return jsonArray;
    }


}


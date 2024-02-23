package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// Represents a Habit, has a name, whether it's completed, the last completed date, and streak
public class Habit implements Writable {
    private String name;
    private boolean completed;
    private String lastCompletedDate;
    private int streak;

    /*
     * REQUIRES: name has a non-zero length
     * EFFECTS: creates a Habit with name set to name;
     *          default completion status false,
     *          no last completed date,
     *          streak so far set to 0
     */
    public Habit(String name) {
        this.name = name;
        this.completed = false;
        this.lastCompletedDate = "never";
        this.streak = 0;

    }

     // EFFECTS: returns String of the name of Habit
    public String getName() {
        return name;
    }

    // EFFECTS: returns completion status of Habit
    public boolean isCompleted() {
        return completed;
    }

    // EFFECTS: returns the last time Habit was completed
    public String getLastCompletedDate() {
        return lastCompletedDate;
    }

    // EFFECTS: returns current streak of Habit
    public int getStreak() {
        return streak;
    }

    // MODIFIES: this, streak, lastCompletedDate
    // EFFECTS: marks habit as completed, increments streak by +1
    // and sets the last completed date to now
    public void markCompleted() {
        this.completed = true;
        streak = streak + 1;
        this.lastCompletedDate = LocalDate.now().toString();
        EventLog.getInstance().logEvent(new Event("Habit: " + getName() + " was marked as completed."));
    }

    // MODIFIES: this
    // EFFECTS: marks habit as skipped
    public void markSkipped() {
        this.completed = false;
        EventLog.getInstance().logEvent(new Event("Habit: " + getName() + " was marked as skipped."));
    }

    // MODIFIES: this, streak
    // EFFECTS: marks habit as incomplete, resets streak to 0
    public void markIncomplete() {
        this.completed = false;
        streak = 0;
        EventLog.getInstance().logEvent(new Event("Habit: " + getName() + " was marked as incomplete."));
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setLastCompletedDate(String lastCompletedDate) {
        this.lastCompletedDate = lastCompletedDate;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("completed", completed);
        json.put("lastCompletedDate", lastCompletedDate);
        json.put("streak", streak);
        return json;
    }

}

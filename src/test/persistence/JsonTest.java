package persistence;

import model.Habit;

import static org.junit.Assert.assertEquals;

public class JsonTest {
    protected void checkHabit(String name, boolean completed, Habit habit, int streak, String lastCompletedDate) {
        assertEquals(name, habit.getName());
        assertEquals(completed, habit.isCompleted());
        assertEquals(streak, habit.getStreak());
        assertEquals(lastCompletedDate, habit.getLastCompletedDate());
    }
}
package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HabitListTest {
    private HabitList habitList;
    private Habit h1;
    private Habit h2;
    private Habit h3;

    @BeforeEach
    public void runBefore() {
        habitList = new HabitList();
        h1 = new Habit("h1");
        h2 = new Habit("h2");
        h3 = new Habit("h3");
    }

    @Test
    public void testAddHabitOnce() {
        habitList.addHabit(h1);
        assertEquals(1, habitList.getHabits().size());
    }

    @Test
    public void testAddHabitMultiple() {
        habitList.addHabit(h1);
        habitList.addHabit(h2);
        assertEquals(2, habitList.getHabits().size());
    }

    @Test
    public void testRemoveHabitNo() {
        habitList.addHabit(h1);
        assertEquals(1, habitList.getHabits().size());
        habitList.removeHabit("h2");
        assertEquals(1, habitList.getHabits().size());
    }

    @Test
    public void testRemoveHabitOne() {
        habitList.addHabit(h1);

        habitList.removeHabit("h1");
        assertEquals(0, habitList.getHabits().size());
    }

    @Test
    public void testAddRemoveHabitMultipleOne() {
        habitList.addHabit(h1);
        habitList.addHabit(h2);

        habitList.removeHabit("h1");
        assertEquals(1, habitList.getHabits().size());
    }

    @Test
    public void testAddRemoveHabitMultiple() {
        habitList.addHabit(h1);
        habitList.addHabit(h2);
        habitList.addHabit(h3);

        habitList.removeHabit("h1");
        habitList.removeHabit("h3");
        assertEquals(1, habitList.getHabits().size());
    }


    @Test
    public void testMarkHabitAsCompletedOne() {
        habitList.addHabit(h1);
        habitList.markHabitAsCompleted("h1");
        assertTrue(habitList.getHabits().get(0).isCompleted());
        assertEquals(1, habitList.getHabits().get(0).getStreak());
    }

    @Test
    public void testMarkHabitAsCompletedMulti() {

        habitList.addHabit(h1);
        habitList.addHabit(h2);
        habitList.addHabit(h3);
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsCompleted("h3");
        assertTrue(habitList.getHabits().get(0).isCompleted());
        assertFalse(habitList.getHabits().get(1).isCompleted());
        assertTrue(habitList.getHabits().get(2).isCompleted());
        assertEquals(1, habitList.getHabits().get(0).getStreak());
        assertEquals(0, habitList.getHabits().get(1).getStreak());
        assertEquals(1, habitList.getHabits().get(2).getStreak());
    }

    @Test
    public void testMarkHabitAsCompletedMultiTimes() {
        habitList.addHabit(h1);
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsCompleted("h1");
        assertTrue(habitList.getHabits().get(0).isCompleted());
        assertEquals(3, habitList.getHabits().get(0).getStreak());
    }

    @Test
    public void testMarkHabitAsCompletedMultiMulti() {

        habitList.addHabit(h1);
        habitList.addHabit(h2);
        habitList.addHabit(h3);
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsCompleted("h2");
        habitList.markHabitAsCompleted("h2");
        assertTrue(habitList.getHabits().get(0).isCompleted());
        assertTrue(habitList.getHabits().get(1).isCompleted());
        assertFalse(habitList.getHabits().get(2).isCompleted());
        assertEquals(1, habitList.getHabits().get(0).getStreak());
        assertEquals(2, habitList.getHabits().get(1).getStreak());
        assertEquals(0, habitList.getHabits().get(2).getStreak());
    }

    @Test
    public void testMarkHabitAsSkippedOne() {
        habitList.addHabit(h1);
        habitList.markHabitAsSkipped("h1");
        assertFalse(habitList.getHabits().get(0).isCompleted());
        assertEquals(0, habitList.getHabits().get(0).getStreak());
    }

    @Test
    public void testMarkHabitNoSkippedOne() {
        habitList.addHabit(h1);
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsSkipped("h1");
        assertFalse(habitList.getHabits().get(0).isCompleted());
        assertEquals(1, habitList.getHabits().get(0).getStreak());
    }

    @Test
    public void testMarkHabitYesSkippedOne() {
        habitList.addHabit(h1);
        habitList.markHabitAsSkipped("h1");
        habitList.markHabitAsCompleted("h1");
        assertTrue(habitList.getHabits().get(0).isCompleted());
        assertEquals(1, habitList.getHabits().get(0).getStreak());
    }

    @Test
    public void testHabitCompleteNoSkipMultiple() {
        habitList.addHabit(h1);
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsSkipped("h1");
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsSkipped("h1");
        assertFalse(habitList.getHabits().get(0).isCompleted());
        assertEquals(3, habitList.getHabits().get(0).getStreak());
    }

    @Test
    public void testHabitCompleteNoSkipMulti() {
        habitList.addHabit(h1);
        habitList.addHabit(h2);
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsSkipped("h1");
        habitList.markHabitAsSkipped("h1");
        habitList.markHabitAsSkipped("h2");
        assertFalse(habitList.getHabits().get(0).isCompleted());
        assertFalse(habitList.getHabits().get(1).isCompleted());
        assertEquals(2, habitList.getHabits().get(0).getStreak());
        assertEquals(0, habitList.getHabits().get(1).getStreak());
    }

    @Test
    public void testHabitCompleteYesSkipMultiple() {
        habitList.addHabit(h1);
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsSkipped("h1");
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsSkipped("h1");
        habitList.markHabitAsCompleted("h1");
        assertTrue(habitList.getHabits().get(0).isCompleted());
        assertEquals(4, habitList.getHabits().get(0).getStreak());
    }



    @Test
    public void testMarkHabitAsIncompleteOnly() {
        habitList.addHabit(h1);
        habitList.markHabitAsIncomplete("h1");
        assertFalse(habitList.getHabits().get(0).isCompleted());
    }

    @Test
    public void testMarkHabitAsIncomplete() {
        habitList.addHabit(h1);
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsIncomplete("h1");
        assertFalse(habitList.getHabits().get(0).isCompleted());
    }

    @Test
    public void testHabitIncompleteMultiple() {
        habitList.addHabit(h1);
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsIncomplete("h1");
        habitList.markHabitAsSkipped("h1");
        habitList.markHabitAsCompleted("h1");
        habitList.markHabitAsIncomplete("h1");
        assertFalse(habitList.getHabits().get(0).isCompleted());
        assertEquals(0, habitList.getHabits().get(0).getStreak());
    }
}
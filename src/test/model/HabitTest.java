package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HabitTest {
    private Habit habit;

    @BeforeEach
    public void setUp() {
        habit = new Habit("Test Habit");
    }

    @Test
    public void testConstructor(){
        assertEquals("Test Habit", habit.getName());
        assertFalse(habit.isCompleted());
        assertEquals(0, habit.getStreak());
    }

    @Test
    public void testCompleteYes() {
        habit.markCompleted();
        assertTrue(habit.isCompleted());
        assertEquals(LocalDate.now().toString(), habit.getLastCompletedDate());
        assertEquals(1, habit.getStreak());
    }

    @Test
    public void testCompleteMultiple() {
        habit.markCompleted();
        habit.markCompleted();
        habit.markCompleted();
        assertTrue(habit.isCompleted());
        assertEquals(3, habit.getStreak());
        assertEquals(LocalDate.now().toString(), habit.getLastCompletedDate());
    }

    @Test
    public void testCompleteNo() {
        habit.markIncomplete();
        assertFalse(habit.isCompleted());
        assertEquals("never", habit.getLastCompletedDate());
        assertEquals(0, habit.getStreak());
    }


    @Test
    public void testCompleteYesStreakBroken() {
        habit.markCompleted();
        habit.markCompleted();
        habit.markIncomplete();
        habit.markCompleted();
        assertEquals(LocalDate.now().toString(), habit.getLastCompletedDate());
        assertTrue(habit.isCompleted());
        assertEquals(1, habit.getStreak());
    }

    @Test
    public void testCompleteNoStreakBroken() {
        habit.markCompleted();
        habit.markCompleted();
        habit.markIncomplete();
        assertFalse(habit.isCompleted());
        assertEquals(0, habit.getStreak());
        assertEquals(LocalDate.now().toString(), habit.getLastCompletedDate());
    }

    @Test
    public void testCompleteStreakBrokenMultiple() {
        habit.markCompleted();
        habit.markCompleted();
        habit.markIncomplete();
        habit.markIncomplete();
        assertFalse(habit.isCompleted());
        assertEquals(0, habit.getStreak());
        assertEquals(LocalDate.now().toString(), habit.getLastCompletedDate());
    }

    @Test
    public void testCompleteYesSkip() {
        habit.markCompleted();
        habit.markCompleted();
        habit.markSkipped();
        assertFalse(habit.isCompleted());
        assertEquals(2, habit.getStreak());
        assertEquals(LocalDate.now().toString(), habit.getLastCompletedDate());
    }

    @Test
    public void testCompleteNoSkip() {
        habit.markCompleted();
        habit.markIncomplete();
        habit.markIncomplete();
        habit.markSkipped();
        assertFalse(habit.isCompleted());
        assertEquals(0, habit.getStreak());
        assertEquals(LocalDate.now().toString(), habit.getLastCompletedDate());
    }

}
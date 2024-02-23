package persistence;

import model.Habit;
import model.HabitList;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            HabitList habitList = new HabitList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    void testWriterEmptyHabitList() {
        try {
            HabitList habitList = new HabitList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHabitList.json");
            writer.open();
            writer.write(habitList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHabitList.json");
            habitList = reader.read();
            assertTrue(habitList.getHabits().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralHabitList() {
        try {
            HabitList habitList = new HabitList();
            Habit Exercise = new Habit("Exercise");
            Habit Read = new Habit("Read");
            habitList.addHabit(Exercise);
            habitList.addHabit(Read);
            habitList.markHabitAsCompleted("Read");
            habitList.markHabitAsCompleted("Read");
            habitList.markHabitAsCompleted("Read");
            Read.setLastCompletedDate("2020-09-04");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHabitList.json");
            writer.open();
            writer.write(habitList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHabitList.json");
            habitList = reader.read();
            List<Habit> habits = habitList.getHabits();
            assertEquals(2, habits.size());

            checkHabit("Exercise", false, habits.get(0),0, "never");
            checkHabit("Read", true, habits.get(1), 3, "2020-09-04");

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
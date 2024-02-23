package persistence;

import model.Habit;
import model.HabitList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            HabitList habitList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    void testReaderEmptyHabitList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHabitList.json");
        try {
            HabitList habitList = reader.read();
            assertTrue(habitList.getHabits().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralHabitList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHabitList.json");
        try {
            HabitList habitList = reader.read();
            List<Habit> habits = habitList.getHabits();
            assertEquals(2, habits.size());

            checkHabit("Exercise", false, habits.get(0), 0, "never");
            checkHabit("Read", true, habits.get(1), 3, "2023-09-04");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}

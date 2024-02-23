package ui;

import model.Habit;
import model.HabitList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

// Habit Tracker Application
public class HabitTracker {
    private HabitList habitList;
    private JsonWriter habitJsonWriter;
    private JsonReader habitJsonReader;
    private Scanner input;

    // EFFECTS: run habit tracker application
    public HabitTracker() throws FileNotFoundException {
        habitList = new HabitList();
        habitJsonWriter = new JsonWriter("./data/habitList.json");
        habitJsonReader = new JsonReader("./data/habitList.json");
        runHabitTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runHabitTracker() {
        boolean isRunning = true;
        String command = null;
        input = new Scanner(System.in);
        System.out.println("\nWelcome to your Habit Tracker ^_^");

        while (isRunning) {
            listHabits();
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("9")) {
                isRunning = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select an option from the menu below!");
        System.out.println("Habit Tracker Menu:");
        System.out.println("1 Load HabitList from file");
        System.out.println("2 Add Habit");
        System.out.println("3 Remove Habit");
        System.out.println("4 Mark Habit As Completed");
        System.out.println("5 Mark Habit As Incomplete");
        System.out.println("6 Mark Habit As Skipped");
        System.out.println("7 List Habits Statistics");
        System.out.println("8 Save file");
        System.out.println("9 Exit");
        System.out.print("Enter your choice: ");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            loadHabitList();
        } else if (command.equals("2")) {
            addHabit();
        } else if (command.equals("3")) {
            removeHabit();
        } else if (command.equals("4")) {
            markHabitAsCompleted();
        } else if (command.equals("5")) {
            markHabitAsIncomplete();
        } else if (command.equals("6")) {
            markHabitAsSkipped();
        } else if (command.equals("7")) {
            listHabits();
        } else if (command.equals("8")) {
            saveHabitList();
        } else {
            System.out.println("Selection not valid... please try again");
        }
    }

    // MODIFIES: this
    // EFFECTS: add habit to application
    private void addHabit() {
        System.out.print("Enter the name of the habit to add: ");
        String habitName = input.next();
        Habit habit = new Habit(habitName);
        habitList.addHabit(habit);
        System.out.println("Habit added: " + habitName);
    }

    // MODIFIES: this
    // EFFECTS: removes habit from application
    private void removeHabit() {
        System.out.print("Enter the name of the habit to remove: ");
        String habitName = input.next();
        habitList.removeHabit(habitName);
        System.out.println("Habit removed: " + habitName);
    }

    // MODIFIES: this
    // EFFECTS: marks habit as complete
    private void markHabitAsCompleted() {
        System.out.print("Enter the name of the habit to mark as completed: ");
        String habitName = input.next();
        habitList.markHabitAsCompleted(habitName);
        System.out.println(habitName + " is marked as completed at" + LocalDate.now());
    }

    // MODIFIES: this
    // EFFECTS: marks habit as incomplete
    private void markHabitAsIncomplete() {
        System.out.print("Enter the name of the habit to mark as incomplete: ");
        String habitName = input.next();
        habitList.markHabitAsIncomplete(habitName);
        System.out.println(habitName + " is marked as incomplete!");
    }

    // MODIFIES: this
    // EFFECTS: marks habit as skipped
    private void markHabitAsSkipped() {
        System.out.print("Enter the name of the habit to mark as skipped: ");
        String habitName = input.next();
        habitList.markHabitAsSkipped(habitName);
        System.out.println(habitName + " is marked as skipped!");
    }

    // MODIFIES: this
    // EFFECTS: displays list of habits and statistics
    private void listHabits() {
        if (habitList.getHabits().isEmpty()) {
            System.out.println("No habits to display.");
        } else {
            System.out.println("Habit List:");
            for (Habit habit : habitList.getHabits()) {
                String status = habit.isCompleted() ? "Completed" : "Not Completed";
                String lastCompleted = habit.getLastCompletedDate()
                        != null ? " (Last Completed: " + habit.getLastCompletedDate() + ")" : "";
                String streak = "Streak: " + habit.getStreak() + "!";
                System.out.println(habit.getName() + " - " + status + lastCompleted + ", " + streak);
            }
        }
    }

    // EFFECTS: saves the habitList to file
    private void saveHabitList() {
        try {
            habitJsonWriter.open();
            habitJsonWriter.write(habitList);
            habitJsonWriter.close();
            System.out.println("Saved habit list to JSON.");
        } catch (FileNotFoundException e) {
            System.out.println("Error saving habit list to JSON.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads habitList from file
    private void loadHabitList() {
        try {
            habitList = habitJsonReader.read();
            System.out.println("Loaded habit list from JSON.");
        } catch (IOException e) {
            System.out.println("Error loading habit list from JSON.");
        }
    }

}
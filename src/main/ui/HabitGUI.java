package ui;

import model.Habit;
import model.HabitList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class HabitGUI {

    // main menu
    private JFrame mainFrame;
    private Dimension frameDimensions;
    private JPanel mainContainer;
    private JLabel welcomeMessage;
    private JLabel welcomeDate;
    private JButton viewHabitsButton;
    private JButton saveButton;
    private JButton exitButton;

    // view habits frame
    private JFrame viewHabitsFrame;
    private JPanel habitsPanel;
    private JButton markHabitButton;
    private JButton addHabitButton;
    private JButton removeHabitButton;
    private HabitList habitList;
    protected HabitsDisplay habitDisplay;
    private DefaultListModel<Habit> habitListModel = new DefaultListModel<>();
    private JList<Habit> habitsList = new JList<>(habitListModel);


    // json
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/habitList.json";

    public HabitGUI() {
        habitList = new HabitList();

        mainFrame = new JFrame();
        frameDimensions = new Dimension(600, 500);
        setFrame();
        mainContainer = setUpMainPanel();
        makeMainContainer();
        mainFrame.add(mainContainer);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("./data/icon.jpg");
        mainFrame.setIconImage(icon.getImage());
        mainFrame.setVisible(true);
        loadHabitsDialog();

        habitDisplay = new HabitsDisplay();

    }

    // MODIFIES: mainFrame
    // EFFECTS: sets up the main frame
    public void setFrame() {
        mainFrame.setTitle("Habit Tracker App");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(frameDimensions.width, frameDimensions.height);
        mainFrame.setBackground(Color.BLUE);

    }

    // MODIFIES: mainFrame
    // EFFECTS: sets up the layout of the main panel
    private JPanel setUpMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 130, 50, 130));
        panel.setBackground(Color.decode("#fff6f9"));

        return panel;
    }

    //MODIFIES: mainFrame
    // EFFECTS: sets up the main container with the welcome message and buttons
    private void makeMainContainer() {
        welcomeMessage = new JLabel("Welcome TO YOUR HabIt TrAcker", SwingConstants.CENTER);
        welcomeMessage.setFont(fontHeader(30));
        welcomeMessage.setForeground(Color.decode("#ffa0c3"));

        welcomeDate = new JLabel("TodAy's dAte: " + LocalDate.now().toString(), SwingConstants.CENTER);
        welcomeDate.setFont(fontHeader(20));
        welcomeDate.setForeground(Color.decode("#ffa0c3"));

        ImageIcon imageIcon = new ImageIcon("./data/img.jpg");
        JLabel imageLabel = new JLabel(imageIcon);

        mainContainer.add(welcomeMessage);
        mainContainer.add(welcomeDate);
        mainContainer.add(imageLabel);
        mainContainer.add(createViewHabitsButton());
        mainContainer.add(createSaveButton());
        mainContainer.add(createExitButton());
    }



    // MODIFIES: mainFrame
    // EFFECTS: creates the view habits button and allows user to view list of all habits when clicked
    private JButton createViewHabitsButton() {
        viewHabitsButton = new JButton("View Habits");
        viewHabitsButton.setFont(fontButton(30));
        viewHabitsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewHabitsFrame();
            }
        });

        return viewHabitsButton;
    }



    // MODIFIES: mainFrame
    // EFFECTS: creates save button and allows user to save their data when clicked
    private JButton createSaveButton() {
        saveButton = new JButton("Save Habits");
        saveButton.setFont(fontButton(30));
        saveButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveHabitListToFile();
            }
        });

        return saveButton;
    }

    // EFFECTS: saves data to file
    private void saveHabitListToFile() {
        JFrame saved = new JFrame("saved");
        saved.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        saved.setSize(300, 200);
        saved.setLayout(null);

        try {
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(habitList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error saving habits to file");
        }

        showInformationDialog(saved);
    }

    // EFFECTS: creates pop-up saying that habits have been saved
    public static void showInformationDialog(JFrame frame) {
        UIManager.put("OptionPane.background", Color.decode("#fff6f9"));
        JOptionPane.showMessageDialog(frame,
                "Your habits has been saved",
                "Saved!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: mainFrame
    // EFFECTS: creates exit button and allows user to quit the app when clicked,
    private JButton createExitButton() {
        exitButton = new JButton("Exit App");
        exitButton.setFont(fontButton(30));
        exitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeApp();
            }
        });

        return exitButton;
    }

    // MODIFIES: mainFrame
    // EFFECTS: closes the app, reminds user to save data with confirmation dialog
    private void closeApp() {
        UIManager.put("OptionPane.background", Color.decode("#fff6f9"));
        int result = JOptionPane.showConfirmDialog(null, "Did you save your data?", "Confirm Save",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (result) {
            case JOptionPane.NO_OPTION:
                System.out.println("No");
                break;
            case JOptionPane.YES_OPTION:
                for (model.Event e : model.EventLog.getInstance()) {
                    System.out.println(e);
                }

                System.exit(0);
        }
    }

    // MODIFIES: mainFrame
    // EFFECTS: displays load habits dialog
    private void loadHabitsDialog() {
        UIManager.put("OptionPane.background", Color.decode("#fff6f9"));
        int result = JOptionPane.showConfirmDialog(null, "Would you like to load an existing list of habits?",
                "Load Habits", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (result) {
            case JOptionPane.NO_OPTION:
                System.out.println("No");
                break;
            case JOptionPane.YES_OPTION:
                try {
                    jsonReader = new JsonReader(JSON_STORE);
                    habitList = jsonReader.read();
                    habitListModel.clear();

                    for (Habit habit : habitList.getHabits()) {
                        habitListModel.addElement(habit);
                    }

                    habitsList.setCellRenderer(new HabitsDisplay());

                } catch (IOException e) {
                    System.out.println("Unable to read from file " + JSON_STORE);
                }
                break;
        }
    }

    // EFFECTS: creates the view habits frame
    private JFrame viewHabitsFrame() {
        setUpHabits();

        habitsPanel = new JPanel(new BorderLayout());
        habitsPanel.add(makeTopPanel(), BorderLayout.NORTH);
        habitsPanel.add(makeListPanel(), BorderLayout.CENTER);
        habitsPanel.add(makeBottomPanel(), BorderLayout.SOUTH);

        viewHabitsFrame.add(habitsPanel);
        viewHabitsFrame.setVisible(true);

        return viewHabitsFrame;

    }


    // MODIFIES: viewHabitsFrame
    // EFFECTS: sets up the view habits frame
    private void setUpHabits() {
        viewHabitsFrame = new JFrame();
        viewHabitsFrame.setSize(frameDimensions);
        viewHabitsFrame.setTitle("My Habits");
        viewHabitsFrame.setLocationRelativeTo(null);
        viewHabitsFrame.setResizable(false);
        viewHabitsFrame.setBackground(Color.BLUE);
    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: constructs the top panel for the frame
    private JPanel makeTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        topPanel.add(createAddHabitButton());
        topPanel.add(createRemoveHabitButton());

        return topPanel;
    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates panel for habits
    private JPanel makeListPanel() {
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#ffa0c3"), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JScrollPane wishListScrollPane = new JScrollPane(habitsList);
        wishListScrollPane.createVerticalScrollBar();
        wishListScrollPane.setHorizontalScrollBar(null);
        selectionPanel.add(wishListScrollPane);

        return selectionPanel;
    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: constructs bottom panel for frame
    private JPanel makeBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottomPanel.add(createCompleteHabitButton());
        bottomPanel.add(createIncompleteHabitButton());
        bottomPanel.add(createSkippedHabitButton());

        return bottomPanel;
    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates mark habit button and allows user to mark habit as completed
    private JButton createCompleteHabitButton() {
        markHabitButton = new JButton("Mark habit as complete");
        markHabitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeDialog();
            }
        });

        return markHabitButton;
    }


    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates mark habit confirmation dialog
    private void completeDialog() {
        int selectedIndex = habitsList.getSelectedIndex();
        Habit selectedHabit = habitListModel.getElementAt(selectedIndex);
        int result = JOptionPane.showConfirmDialog(null, "Mark " + selectedHabit.getName() + " as complete?",
                "Mark as complete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (result) {
            case JOptionPane.NO_OPTION:
                break;

            case JOptionPane.YES_OPTION:

                if (selectedIndex != 1) {
                    selectedHabit.markCompleted();
                }
        }

    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates mark habit button and allows user to mark habit as completed
    private JButton createIncompleteHabitButton() {
        markHabitButton = new JButton("Mark habit as incomplete");
        markHabitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incompleteDialog();
            }
        });

        return markHabitButton;
    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates mark habit confirmation dialog
    private void incompleteDialog() {
        int selectedIndex = habitsList.getSelectedIndex();
        Habit selectedHabit = habitListModel.getElementAt(selectedIndex);
        int result = JOptionPane.showConfirmDialog(null, "Mark " + selectedHabit.getName() + " as incomplete?",
                "Mark as incomplete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (result) {
            case JOptionPane.NO_OPTION:
                break;

            case JOptionPane.YES_OPTION:

                if (selectedIndex != 1) {
                    selectedHabit.markIncomplete();
                }
        }

    }


    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates mark habit button and allows user to mark habit as completed
    private JButton createSkippedHabitButton() {
        markHabitButton = new JButton("Mark habit as skipped");
        markHabitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skippedDialog();
            }
        });

        return markHabitButton;
    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates mark habit confirmation dialog
    private void skippedDialog() {
        int selectedIndex = habitsList.getSelectedIndex();
        Habit selectedHabit = habitListModel.getElementAt(selectedIndex);
        int result = JOptionPane.showConfirmDialog(null, "Mark " + selectedHabit.getName() + " as skipped?",
                "Mark as incomplete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (result) {
            case JOptionPane.NO_OPTION:
                break;

            case JOptionPane.YES_OPTION:

                if (selectedIndex != 1) {
                    selectedHabit.markSkipped();
                }
        }

    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates add habit button and allows user to add a habit to list
    private JButton createAddHabitButton() {
        addHabitButton = new JButton("Add New Habit!");
        addHabitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDialog();
            }
        });

        return addHabitButton;
    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates add habit confirmation dialog
    private void addDialog() {
        String habitName = JOptionPane.showInputDialog(mainFrame, "Enter habit name:");
        if (habitName != null && !habitName.trim().isEmpty()) {
            Habit habit = new Habit(habitName);
            habitList.addHabit(habit);
            habitListModel.addElement(habit);
            habitsList.setCellRenderer(habitDisplay);
            JOptionPane.showMessageDialog(mainFrame, "Habit added: " + habitName + "!");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a valid habit name!");
        }
    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates remove habit button and allows user to delete selected habit from list
    private JButton createRemoveHabitButton() {
        removeHabitButton = new JButton("Remove Selected Habit");
        removeHabitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeDialog();
            }
        });

        return removeHabitButton;
    }

    // MODIFIES: viewHabitsFrame
    // EFFECTS: creates remove habit confirmation dialog
    private void removeDialog() {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this habit?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (result) {
            case JOptionPane.NO_OPTION:
                break;

            case JOptionPane.YES_OPTION:
                int selectedIndex = habitsList.getSelectedIndex();
                Habit habit = habitListModel.getElementAt(selectedIndex);
                habitList.removeHabit(habit.getName());
                habitListModel.remove(selectedIndex);
        }
    }

    // EFFECTS: creates a custom font for the header
    public static Font fontHeader(int size) {
        Font customFont = null;
        try {
            File fontFile = new File("./data/Starborn.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // Handle font loading exception
            // You can set a default font if the custom font fails to load
            customFont = new Font("Arial", Font.PLAIN, size);
        }
        return customFont;
    }

    // EFFECTS: creates a custom font for buttons
    public static Font fontButton(int size) {
        Font customFont = null;
        try {
            File fontFile = new File("./data/Winkle.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // Handle font loading exception
            // You can set a default font if the custom font fails to load
            customFont = new Font("Arial", Font.PLAIN, size);
        }
        return customFont;
    }
}


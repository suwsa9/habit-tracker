package ui;

import model.Habit;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HabitsDisplay implements ListCellRenderer<Habit> {
    private JPanel panel;
    private JPanel detailsPanel;
    private JLabel name;
    private JLabel completed;
    private JLabel streak;
    private JLabel date;
    private Habit value;
    private JList<? extends Habit> list;


    // EFFECTS: constructs the render
    public HabitsDisplay() {
        panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        name = new JLabel();
        completed = new JLabel();
        streak = new JLabel();
        date = new JLabel();

        createDetailsPanel();

        panel.add(detailsPanel, BorderLayout.CENTER);

    }


    private JPanel createDetailsPanel() {
        detailsPanel = new JPanel(new GridLayout(3, 2));
        detailsPanel.add(name);
        detailsPanel.add(completed);
        detailsPanel.add(streak);
        detailsPanel.add(date);

        return detailsPanel;
    }


    @Override
    public Component getListCellRendererComponent(JList<? extends Habit> list,
                                                  Habit value, int index, boolean isSelected, boolean cellHasFocus) {
        this.value = value;
        this.list = list;
        setUp();
        if (isSelected) {
            isSelected();

        } else {
            notSelected();
        }
        if (cellHasFocus) {
            detailsPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#ffa0c3")));
        } else {
            detailsPanel.setBorder(BorderFactory.createEmptyBorder());
        }

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: assigns data to its appropriate field, creates the displayed version of data
    private void setUp() {
        name.setText("name: " + value.getName());
        completed.setText("completed? " + value.isCompleted());
        streak.setText("streak: " + value.getStreak());
        date.setText("last completed: " + value.getLastCompletedDate());

        name.setOpaque(true);
        completed.setOpaque(true);
        streak.setOpaque(true);
        date.setOpaque(true);
    }



    // MODIFIES: this
    // EFFECTS: sets background for selected habit
    private void isSelected() {
        streak.setBackground(list.getSelectionBackground());
        name.setBackground(list.getSelectionBackground());
        completed.setBackground(list.getSelectionBackground());
        date.setBackground(list.getSelectionBackground());
    }

    // MODIFIES: this
    // EFFECTS: sets background for unselected habit
    private void notSelected() {
        detailsPanel.setBorder(BorderFactory.createLineBorder(list.getBackground(), 10));
        streak.setBackground(list.getBackground());
        name.setBackground(list.getBackground());
        completed.setBackground(list.getBackground());
        date.setBackground(list.getBackground());
    }
}

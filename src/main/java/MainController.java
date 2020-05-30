import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class MainController {

    @FXML private GridPane exerciseContainer;
    @FXML private Label upNextLabel;

    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    private int maxSets;

    @FXML
    public void initialize () {
        exerciseList.add(new Exercise("Pullups", "6 6 5 4 3"));
        exerciseList.add(new Exercise("Bicep Curls", "20 20 20"));
        exerciseList.add(new Exercise("DB Skullcrushers", "14 14 14"));
        for (Exercise exercise : exerciseList) {
            if (exercise.getSets().size() > maxSets) maxSets = exercise.getSets().size();
        }
        addExercises();
    }

    public void addExercises () {
        int rowCounter = 0;
        for (Exercise exercise : exerciseList) {
            exerciseContainer.addRow(rowCounter);

            int numSets = exercise.getSets().size();
            for (int i = 0; i < numSets; i++) {
                Label tempLabel = new Label(String.valueOf(exercise.getSets().get(i).reps));
                tempLabel.setTextFill(Color.RED);
                tempLabel.setFont(new Font("Franklin Gothic Medium", 30));
                tempLabel.setOnMouseClicked(event -> {
                    if (event.getSource() instanceof Label) {
                        if (((Label) event.getSource()).getTextFill() == Color.RED) {
                            ((Label) event.getSource()).setTextFill(Color.GREEN);
                        } else {
                            ((Label) event.getSource()).setTextFill(Color.RED);
                        }
                    }
                });
                exerciseContainer.add(tempLabel, i+1, rowCounter);
            }
            Label tempLabel = new Label(exercise.getName());
            tempLabel.setFont(new Font("Franklin Gothic Medium", 15));
            exerciseContainer.add(tempLabel, 0, rowCounter);
            exerciseContainer.add(new Button("History"), maxSets+1, rowCounter);
            exerciseContainer.add(new Button("Edit"), maxSets+1, rowCounter+1);
            exerciseContainer.add(new Button("Delete"), maxSets+1, rowCounter+2);
            rowCounter+=3;
        }
        balanceColumns();
    }

    private void balanceColumns () {
        int numCols = exerciseContainer.getColumnCount();
        if (numCols == 0) return;

        double containerWidth = exerciseContainer.getPrefWidth();
        double setLabelWidth = (containerWidth - 200) / maxSets;

        // first column is for the exercise name, last column is for the buttons so special consideration for these
        exerciseContainer.getColumnConstraints().clear();
        exerciseContainer.getColumnConstraints().add(0, new ColumnConstraints(125));
        for (int i = 1; i < numCols - 1; i++) {
            exerciseContainer.getColumnConstraints().add(i, new ColumnConstraints(setLabelWidth));
        }
        exerciseContainer.getColumnConstraints().add(numCols-1, new ColumnConstraints(75));
    }

}
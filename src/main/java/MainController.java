import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class MainController {

    @FXML private GridPane exerciseContainer;
    @FXML private Label upNextLabel;

    private ArrayList<Exercise> exerciseList = new ArrayList<>();

    @FXML
    public void initialize () {
        exerciseList.add(new Exercise("Pullups", "5 4 3"));
        exerciseList.add(new Exercise("Dips", "5 4 3"));
        addExercises();
    }

    public void addExercises () {
        for (Exercise exercise : exerciseList) {
            exerciseContainer.addRow(exerciseContainer.getRowCount());
            exerciseContainer.add(new Label(exercise.getName()), 0, exerciseContainer.getRowCount());
            exerciseContainer.add(new Button("History"), 1, exerciseContainer.getRowCount());
            exerciseContainer.add(new Button("Edit"), 1, exerciseContainer.getRowCount());
            exerciseContainer.add(new Button("Delete"), 1, exerciseContainer.getRowCount());
        }
    }

}
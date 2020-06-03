import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private Button resetAllButton;
    @FXML private GridPane exerciseContainer;
    @FXML private Label upNextLabel;

    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    private int maxSets;
    private ArrayList<Label> labelList = new ArrayList<>();

    @FXML
    public void initialize () {
        try {
            readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exerciseList.isEmpty()) {
            exerciseList.clear();
            exerciseList.add(new Exercise("Pullups +8kg", "7 6 5 4 3"));
            exerciseList.add(new Exercise("Push ups +8kg", "15 15 15 15"));
            exerciseList.add(new Exercise("Bicep Curls", "20 20 20 20"));
            exerciseList.add(new Exercise("DB Shoulder Press", "20 20 20 20"));
            exerciseList.add(new Exercise("DB Skullcrushers", "15 15 15 15"));
            exerciseList.add(new Exercise("Plank +8kg", "60 60 60 60"));
            exerciseList.add(new Exercise("Ab roller", "10 10 10 10"));
        }
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
                if (exercise.getSets().get(i).completed) {
                    tempLabel.setTextFill(Color.GREEN);
                } else {
                    tempLabel.setTextFill(Color.RED);
                }
                tempLabel.setFont(new Font("Franklin Gothic Medium", 30));
                int finalI = i;
                tempLabel.setOnMouseClicked(event -> {
                    if (event.getSource() instanceof Label) {
                        exercise.getSets().get(finalI).completed = !exercise.getSets().get(finalI).completed;
                        if (((Label) event.getSource()).getTextFill() == Color.RED) {
                            ((Label) event.getSource()).setTextFill(Color.GREEN);
                        } else {
                            ((Label) event.getSource()).setTextFill(Color.RED);
                        }
                        try {
                            writeToFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                labelList.add(tempLabel);
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

    public void resetAllClicked(MouseEvent mouseEvent) {
        for (Label l : labelList) {
            l.setTextFill(Color.RED);
        }
        for (Exercise e : exerciseList) {
            for (Set s : e.getSets()) {
                s.completed = false;
            }
        }
        try {
            writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile () throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        File directory = new File(System.getProperty("user.home") + "\\WorkoutTracker");
        if (!directory.exists()) directory.mkdir();

        objectMapper.writeValue(new File(System.getProperty("user.home") + "\\WorkoutTracker\\exerciseList.json"), exerciseList);
    }

    private void readFromFile () throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        exerciseList = objectMapper.readValue(new File(System.getProperty("user.home") + "\\WorkoutTracker\\exerciseList.json"), new TypeReference<ArrayList<Exercise>>(){});
    }
}
import java.util.ArrayList;

public class Exercise {

    private String name;
    private ArrayList<Set> sets = new ArrayList<>();


    public Exercise(String name, ArrayList<Set> sets) {
        this.name = name;
        this.sets = sets;
    }

    public Exercise(String name, String input) { // setsReps format "5 4 3"
        this.name = name;
        String[] arrayReps = input.split(" ");
        for (String string : arrayReps) {
            sets.add(new Set(Integer.parseInt(string)));
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }
}

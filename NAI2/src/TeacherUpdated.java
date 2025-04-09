import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherUpdated {


    List<Perceptron> perceptrons;
    List<String> languages;                              // -> wiemy czy polski, angielski, hiszpanski czy inne
    Map<String,Integer> languageLabels;
    double learningRate;
    private final int inputDimension = 26;                         // -> 26 liter alfabetu




    public TeacherUpdated(List<String> languages, double learningRate) {
        this.languages = new ArrayList<String>(languages);
        this.learningRate = learningRate;
        this.languageLabels = new HashMap<String,Integer>();
        this.perceptrons = new ArrayList<>();


        // -> dobra a teraz inicjalizacja perceptronow dla kazdej z grupy jezykow

        for (int i = 0; i < languages.size(); i++) {
            this.languageLabels.put(languages.get(i), i);
            this.perceptrons.add(new Perceptron(inputDimension, learningRate));
        }

    }




}

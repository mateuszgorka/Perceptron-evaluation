import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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



    public List<DataForLanguage> loadData(String filePath) throws IOException {


        List<DataForLanguage> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",",2);
                if (tokens.length != 2) {

                    String language = tokens[0].trim().replace("\"","");
                    String text = tokens[1].trim().replace("\"","");

                    if (languageLabels.containsKey(language)) {
                        data.add(new DataForLanguage(language, text));
                    } else {
                        System.out.println("Language " + language + " not found");
                    }
                } else {
                    System.out.println("Language " + tokens[0].trim().replace("\"","") + " not found");
                }
            }
        }

        return data;
    }







}

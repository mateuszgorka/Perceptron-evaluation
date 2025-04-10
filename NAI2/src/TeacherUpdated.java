import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

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



    public void trainData(List<DataForLanguage> trainingData, int epochs) throws IOException {

        System.out.println("Rozpoczecie trenigu dla " + epochs + " epok");

        for (int epoch = 0; epoch < epochs; epoch++) {

            int erorrsInEpoch = 0;  // -> do debuggowania


            for (DataForLanguage data : trainingData) {

                String language = data.getLanguage();
                String text = data.getText();
                List<Double> input =  ProcessedText.textVector(text);           // -> przetwarzamy ten tekst na wektor cech


                int targetedIndex = languageLabels.get(language);


                // ---------------
                // -> Musimy przetworzyc wejscie przez KAZDY perceptron i obliczyc dla niego blad

                double maxOutputForPrediction = -Double.MAX_VALUE;
                int predictedIndexForErrorCount = -1;



                // teraz bedziemy iterowac przez wszystkie perceptrony [X]


                for (int i = 0; i < perceptrons.size(); i++) {

                    Perceptron perceptron = perceptrons.get(i);


                    // teraz obliczamy prog aktywacji i elo (compute)
                    double activation = perceptron.compute(input);

                    // -> sprawdzamy ziomeczki czy perceptron dał najwyzsze wyjscie do tej pory
                    if (activation > maxOutputForPrediction) {
                        maxOutputForPrediction = activation;
                        predictedIndexForErrorCount = i;
                    }


                    double targetedValue = (i == targetedIndex) ? 1 : 0; // -> wartosc docelowa dla i-tego perceptronu

                    double error = targetedValue - activation;              // -> blad to roznica miedzy oczekiwana a uzskana

                    // -> mamy input i blad wiec mozemy dostoswac wagi learnem

                    perceptron.learn(input, error);

                    if (predictedIndexForErrorCount != targetedIndex) {
                        erorrsInEpoch++;
                    }
                }
            }
            System.out.println(epoch + " zakonczona - liczba bledow " + erorrsInEpoch);
        }

        System.out.println("Trening ended");
    }






}

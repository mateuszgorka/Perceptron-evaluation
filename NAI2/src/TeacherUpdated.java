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



    // -> tutaj wpisujemy ten pjedynczy tekst dostepny do wpisania dla uytkownika!!!!!
    public String preditction(String text){

        // -> tekst wejsciowy na cechy as perviously done
        List<Double> inputV = ProcessedText.textVector(text);

        double maxOut = -Double.MAX_VALUE;
        int predictedIndex = -1;


        // -> teraz musze przeiterowac wszystkie perceptrony
        // -> i obliczyc aktywacje


        for (int i = 0; i < perceptrons.size(); i++){

            double output = perceptrons.get(i).compute(inputV);

            if (output > maxOut){
                maxOut = output;
                predictedIndex = i;
            }
        }
        if (predictedIndex != -1){

            return languages.get(predictedIndex);
        }

        return "Nie ma takiego i elo";
    }


    // dobra jeszcze chcemy wpisac evaluator -> wypisywanie macierzy itp itd
    public void evaluate(List<DataForLanguage> testData){




        int numClasses = languages.size();
        int[][] confusionMatrix = new int[numClasses][numClasses];
        int correctPredictions = 0;
        int totalPredictions = testData.size();




        for (DataForLanguage data : testData) {
            String actualLanguage = data.getLanguage();
            String text = data.getText();
            String predictedLanguage = preditction(text);

            int actualIndex = languageLabels.get(actualLanguage);
            int predictedIndex = languageLabels.getOrDefault(predictedLanguage, -1);

            if (predictedIndex != -1) {
                confusionMatrix[actualIndex][predictedIndex]++;
            }

            if (actualLanguage.equals(predictedLanguage)) {
                correctPredictions++;
            }
        }


        double overallAccuracy = (totalPredictions > 0) ? (double) correctPredictions / totalPredictions * 100.0 : 0.0;



        double[] precision = new double[numClasses];
        double[] recall = new double[numClasses];
        double[] fScale = new double[numClasses];


        // _> obliczanie mordeczko Precision, Recall, F-scale dla każdej klasy

        for (int i = 0; i < numClasses; i++) {
            double tp = confusionMatrix[i][i];
            double fp = 0;
            double fn = 0;

            for (int j = 0; j < numClasses; j++) {
                if (j != i) {
                    fp += confusionMatrix[j][i];
                    fn += confusionMatrix[i][j];
                }
            }

            precision[i] = (tp + fp) > 0 ? tp / (tp + fp) : 0;
            recall[i] = (tp + fn) > 0 ? tp / (tp + fn) : 0;
            fScale[i] = (precision[i] + recall[i]) > 0 ? 2 * precision[i] * recall[i] / (precision[i] + recall[i]) : 0;
        }


        double macroAvgPrecision = 0;
        double macroAvgRecall = 0;
        double macroAvgFScore = 0;


        for (int i = 0; i < numClasses; i++) {
            macroAvgPrecision += precision[i];
            macroAvgRecall += recall[i];
            macroAvgFScore += fScale[i];

        }


        macroAvgPrecision /= numClasses;
        macroAvgRecall /= numClasses;
        macroAvgFScore /= numClasses;



        System.out.println("\n--- Wyniki Ewaluacji ---");


        System.out.printf("Ogólna Dokładność (Accuracy): %.2f%% (%d/%d)\n", overallAccuracy, correctPredictions, totalPredictions);
        System.out.println("\nMiary Makro-średnie:");


        System.out.printf("  Macro Avg Precision: %.4f\n", macroAvgPrecision);
        System.out.printf("  Macro Avg Recall:    %.4f\n", macroAvgRecall);
        System.out.printf("  Macro Avg F1-Score:  %.4f\n", macroAvgFScore);

    }

}

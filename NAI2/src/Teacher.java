import java.util.ArrayList;
import java.util.List;

public class Teacher {

    Perceptron perceptron;
    List<List<Double>> learnData;
    List<List<Double>> testData;
    int epochs;


    int[][] confusionMatrix;



    public Teacher(String learnFile, String testFile,int epochs) {
        this.epochs = epochs;

        this.learnData = new ArrayList<>();
        Reading.readCSV(learnFile, this.learnData);

        this.testData = new ArrayList<>();
        Reading.readCSV(testFile, this.testData);


        if (learnData != null && !learnData.isEmpty() && learnData.get(0).size() > 1) {
            int dimension = learnData.getFirst().size() - 1;
            this.perceptron = new Perceptron(dimension);
        }

        confusionMatrix = new int[2][2];   // -> inicjalizujemy macierz pomyłek i elo
    }

    public void teachPerceptron(){

        // [1.2,.3.3,2.3,1.0]
        // [...][1.0] -> compute sam wektor i przyrownanie do [1.0]
        // jezeli sie zgadza to zajebiscie
        // jezeli nie to learn() -> ilość epok (iteracji) przez całe List<List<Double>>
        // epok do wpisania, blabla


         for (int i = 0; i < epochs; i++) {  // -> iterowanie po epokach
             for (List<Double> data : learnData) {     // -> Iterowanie po wierszach danych
                 List<Double> features = data.subList(0, data.size() - 1); // -> wyodrebnienie wag do porownania i potem do compute(owania)
                 double label = data.get(data.size() - 1);

                 perceptron.learn(features, label);  // -> treningujemy perceptron i dostajemy
             }

             System.out.println("Epoka " + (i + 1) + " zakończona");   // -> trzeba popatrzec kiedy sie wylacza kiedy nie
             System.out.println("Zmiana thresholdu " + perceptron.threshold);
         }


         System.out.println("Trening perceptronu zakończony");
    }



    public double testPerceptron(){
        // ide po tych z data() -> robie compute() bez 1.0/0.0
        // porownuje compute() z tym 1.0/0.0 i patrze il % jest poprawnych

        int correctOutputs = 0;


        // ----- trzeba wyzerowac macierz pomyłek przed kazdym testowaniem -------
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                confusionMatrix[i][j] = 0;
            }
        }
        // -----------------------------------------------------------------------


        for (List<Double> data : testData) {
            List<Double> features = data.subList(0, data.size() - 1);  // -> znowu wyodrebniamy [2.3,2.3,4.2][1.0]   [wektor ktory compute(ujemy)][typ grupy 1.0/0.0]
            double possibleLabel = data.get(data.size() - 1);


            double predictedLabel = perceptron.compute(features);   // -> czyli robimy compute dla wektora


            confusionMatrix[(int)possibleLabel][(int)predictedLabel]++;


            if (predictedLabel == possibleLabel) {
                correctOutputs++;
            }
        }
            // ------------- teraz czesc za acuraccy -------------

        double accuracy = ((double) correctOutputs / (double) testData.size()) * 100;
        return accuracy;
    }

    public void runEvaluator(String testFile) {
        Evaluator evaluator = new Evaluator(testFile, perceptron, testData, confusionMatrix);
        evaluator.printConfusionMatrix();
        System.out.println();
        evaluator.evaluate();
    }
}

import java.util.List;

public class Evaluator {

    String fileToTest;
    List<List<Double>> dataFromPerceptron;
    Perceptron perceptron;
    int[][] confusionMatrix;

    int numericClasses;



    public Evaluator(String fileToTest, Perceptron perceptron, List<List<Double>> dataFromPerceptron, int[][] confusionMatrix, int numericClasses) {
        this.fileToTest = fileToTest;
        this.perceptron = perceptron;
        this.dataFromPerceptron = dataFromPerceptron;
        this.confusionMatrix = confusionMatrix;
        this.numericClasses = numericClasses;
    }


    public void evaluate() {
        if (numericClasses == 2) {
            evaluateBinaryClassification();
        } else {
            evaluateMultiClassification();
        }
    }




    public void evaluateBinaryClassification(){

        double accuracy = (double)(confusionMatrix[0][0] + confusionMatrix[1][1]) /
                (confusionMatrix[0][0] + confusionMatrix[1][0] + confusionMatrix[0][1] + confusionMatrix[1][1]);

        double precision = confusionMatrix[1][1] + confusionMatrix[0][1] != 0 ?
                (double) confusionMatrix[1][1] / (confusionMatrix[1][1] + confusionMatrix[0][1]) : 0;

        double recall = confusionMatrix[1][1] + confusionMatrix[1][0] != 0 ?
                (double) confusionMatrix[1][1] / (confusionMatrix[1][1] + confusionMatrix[1][0]) : 0;

        double f_scale = precision + recall != 0 ? (2 * precision * recall) / (precision + recall) : 0;

        System.out.println("Miary Ewaluacji: ");
        System.out.println("Accuracy [evaluation]: " + accuracy);
        System.out.println("Precision [evaluation]: " + precision + " /jezeli wynosi 1.0 to znaczy ze perceptron nigdy blednie nie przewidzial klasy 1");
        System.out.println("Recall [evaluation]: " + recall);
        System.out.println("F-Scale [evaluation]: " + f_scale);


        System.out.println("-------------------------------");
        System.out.println("Macierz Omylek:");
        System.out.println("------------------");
        System.out.println("| ->       | " + confusionMatrix[0][0] + " | " + confusionMatrix[0][1] + " |" + "  |(TruePostive) Poprawnie sklasyfikowana klasa 0|   |(FalsePositive) Zle sklasyfikowana klasa 0|");
        System.out.println("| ->       | " + confusionMatrix[1][0] + " | " + confusionMatrix[1][1] + " |" + "  |(FalseNegative) Zle sklasyfikowana klasa 1|   |(TrueNegative) Poprawnie sklasyfikowana klasa 1|");
        System.out.println("------------------");
    }


    public void evaluateMultiClassification() {

        double[] precision = new double[numericClasses];
        double[] recall = new double[numericClasses];
        double[] fScale = new double[numericClasses];
        double[] classAcu = new double[numericClasses];



        // -> tu obliczamy Precision, Recalls, F-scale dla kazdych klas
        for (int i = 0; i < numericClasses; i++) {
            double tp = confusionMatrix[i][i];   // -> true positivies dla klasy "i"
            double fp = 0;
            double fn = 0;
            double totalClasses = 0;

            for (int j = 0; j < numericClasses; j++) {
                if (j != i) {
                    fp += confusionMatrix[j][i];
                    fn += confusionMatrix[j][j];
                }
                totalClasses += confusionMatrix[j][j];
            }


            // OBLICZANIE WARTOSCI
            precision[i] = (tp + fp) > 0 ? tp / (tp + fp) : 0;  // -> nie dzielimy przez zero cholero wiec trza sprawdzic
            recall[i] = (tp + fn) > 0 ? tp / (tp + fn) : 0;  // same
            fScale[i] = (precision[i] + recall[i]) > 0 ? 2 * precision[i] * recall[i] / (precision[i] + recall[i]) : 0;
            classAcu[i] = totalClasses > 0 ? tp / totalClasses : 0;

        }

            // -> teraz makro-srednia
//            Oto jak obliczyć makro średnią:
//            Oblicz metrykę (np. precyzję) dla każdej klasy.
//            Zsumuj wartości metryki dla wszystkich klas.
//            Podziel sumę przez liczbę klas.


            // -> czyli np
//            Precyzja dla klasy A: 0.8
//            Precyzja dla klasy B: 0.7
//            Precyzja dla klasy C: 0.9
//            Makro średnia precyzji wynosi: (0.8 + 0.7 + 0.9) / 3 = 0.8




            double macroAvgPrecision = 0;
            double macroAvgRecall = 0;
            double macroAvgFScore = 0;
            double macroAvgAccuracy = 0;



            for (int i = 0; i < numericClasses; i++) {
                macroAvgPrecision += precision[i];
                macroAvgRecall += recall[i];
                macroAvgFScore += fScale[i];
                macroAvgAccuracy += classAcu[i];
            }



            // DZIEEEEEEEEEELIMY na ilosc klas zeby avarage ogarnac
            macroAvgPrecision /= numericClasses;
            macroAvgRecall /= numericClasses;
            macroAvgFScore /= numericClasses;
            macroAvgAccuracy /= numericClasses;


        System.out.println("Miary Ewaluacji dla tych multiple:");
        System.out.println();
        System.out.println("Accuracy: " + macroAvgAccuracy);
        System.out.println("Macro Avg Precision: " + macroAvgPrecision);
        System.out.println("Macro Avg Recall: " + macroAvgRecall);
        System.out.println("Macro Avg FScore: " + macroAvgFScore);


        System.out.println();
        System.out.println();
        System.out.println();


        // -> teraz bedziemy liczyc i ogolnie printowac macierz omylek dla (n) klas


        for (int i = 0; i < numericClasses; i++) {
            System.out.println("| ->     |");
            for (int j = 0; j < numericClasses; j++) {
                System.out.println(confusionMatrix[i][j]);
            }
        }





    }
}

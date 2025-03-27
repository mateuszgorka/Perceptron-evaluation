import java.util.List;

public class Evaluator {

    String fileToTest;
    List<List<Double>> dataFromPerceptron;
    Perceptron perceptron;
    int[][] confusionMatrix;



    public Evaluator(String fileToTest, Perceptron perceptron, List<List<Double>> dataFromPerceptron, int[][] confusionMatrix) {
        this.fileToTest = fileToTest;
        this.perceptron = perceptron;
        this.dataFromPerceptron = dataFromPerceptron;
        this.confusionMatrix = confusionMatrix;
    }


    public void evaluate(){

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
    }


    public void printConfusionMatrix() {
        System.out.println("Macierz Omyłek:");
        System.out.println("------------------");
        System.out.println("| ->       | " + confusionMatrix[0][0] + " | " + confusionMatrix[0][1] + " |" + "  |(TruePostive) Poprawnie sklasyfikowana klasa 0|   |(FalsePositive) Zle sklasyfikowana klasa 0|");
        System.out.println("| ->       | " + confusionMatrix[1][0] + " | " + confusionMatrix[1][1] + " |" + "  |(FalseNegative) Zle sklasyfikowana klasa 1|   |(TrueNegative) Poprawnie sklasyfikowana klasa 1|");
        System.out.println("------------------");
    }
}

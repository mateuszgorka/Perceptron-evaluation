import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        String learnFiles = "C:\\Users\\gorka\\Downloads\\OneDrive_1_20.03.2025\\perceptron.data";
        String testFiles = "C:\\Users\\gorka\\Downloads\\OneDrive_1_20.03.2025\\perceptron.test.data";
        int epochs = 100;

        Teacher teacher = new Teacher(learnFiles, testFiles, epochs);
        teacher.teachPerceptron();
        double accuracy = teacher.testPerceptron();


        System.out.println("[ Accuracy " + accuracy + " % ]");
        System.out.println();
        teacher.printConfusionMatrix();

    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        String learnFiles = "perceptron.data";
        String testFiles = "perceptron.test.data";


//        System.out.print("Wpisz wartosc epok: ");
//        Scanner scan = new Scanner(System.in);
//        int epochs = scan.nextInt();
//
        int epochs = 100;
        Teacher teacher = new Teacher(learnFiles, testFiles, epochs);
        teacher.teachPerceptron();
        double accuracy = teacher.testPerceptron();


        System.out.println("[ Accuracy " + accuracy + " % ]");
        System.out.println();
        teacher.runEvaluator(testFiles);

    }
}
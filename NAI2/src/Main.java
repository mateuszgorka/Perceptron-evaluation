import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        String trainFilePath = "data/lang.train.csv";
        String testFilePath = "data/lang.test.csv";


        List<String> langs = List.of("Polish", "English", "Spanish", "German");

        double learningRate = 0.01;
        int epochs = 100;

        TeacherUpdated tu = new TeacherUpdated(langs, learningRate);

        try {

            List<DataForLanguage> trainData = tu.loadData(trainFilePath);
            System.out.println("Wczytano " + trainData.size() + " przykladow testowych");

            List<DataForLanguage> testData = tu.loadData(testFilePath);
            System.out.println("Wczytano " + testData.size() + " przykladow");


            // treningujemy

            tu.trainData(trainData, epochs);
            tu.evaluate(testData);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
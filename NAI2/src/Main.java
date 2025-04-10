import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        String trainFilePath = "data/lang.train.more.csv";
        String testFilePath = "data/lang.test.more.csv";


        List<String> langs = List.of("Polish", "English", "Spanish", "German","French");
//        List<String> langs = List.of("Polish", "English", "Spanish", "German");

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




            // -> teraz mechanizm wpisywania recznego <>
            System.out.println();
            System.out.println("Testowanie reczne: ");
            System.out.println("Wpisz exit aby zakonczyc");
            System.out.println();
            Scanner scan = new Scanner(System.in);


            List<String> knownLanguages = tu.getLanguage();


            while (true) {

            System.out.print("> ");
            String line = scan.nextLine();


            if (("exit").equals(line)) {
                break;
            }

            // metoda predykcji i elo

            String predictedLang = tu.preditction(line);

            if (knownLanguages.contains(predictedLang)) {
                System.out.println("  Rozpoznany jezyk: " + predictedLang);
            } else {
                System.out.println("  Nie rozpoznano jezyka ");
            }
        }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
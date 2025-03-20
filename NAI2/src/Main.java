import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

public static void main(String[] args) {




    List<String> perceptronList = new ArrayList<>();


    try {
        perceptronList = read("C:\\Users\\s31289\\Downloads\\OneDrive_1_14.03.2025\\perceptron.data");
        for (String line : perceptronList) {
            System.out.println(line);
        }
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
    }
}




    public static List<String> read(String fileName) throws FileNotFoundException, IOException {
        List<String> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        }

        return data;
    }
}
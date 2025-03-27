import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reading {

    public static void readCSV(String filePath, List<List<Double>> dataList, Map<String, Double> labelMap) {


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                List<Double> row = new ArrayList<>();

                for (int i = 0; i < data.length - 1; i++) {
                    row.add(Double.parseDouble(data[i]));
                }


                String label = data[data.length - 1];

                if (!labelMap.containsKey(label)) {
                    labelMap.put(label, labelMap.isEmpty() ? 1.0 : 0.0);
                }

                double numericLabel = labelMap.get(label);
                row.add(numericLabel);
                dataList.add(row);




                // (tu byl hardcode ale nie bedzie!)
//                if (data[data.length - 1].equals("Iris-versicolor")) {
//                    row.add(1.0);
//                } else {
//                    row.add(0.0);
//                }

            }
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
        }
    }
}
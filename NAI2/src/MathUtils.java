import java.util.List;

public class MathUtils {


    // obliczenie iloczynu skalarnego dwóch wektorów
    public static double iloczynSkalarnyLicz(List<Double> list1, List<Double> list2) {
        double result = 0;
        for (int i = 0; i < list1.size(); i++) {
            result += list1.get(i) * list2.get(i);
        }
        return result;
    }
}

import java.util.ArrayList;
import java.util.List;

public class Perceptron {


    List<Double> weights;   // -> lista wag
    double threshold;       // -> próg aktywacji
    double alpha;           // -> współczynnik uczenia dla wag
    double beta;            // ->           -||-       dla progu aktywacji
    int dimension;  //  dopuszczalna dlugosc wektora wag/wejsc


    public Perceptron(int dimension) {
        this.dimension = dimension;
        weights = new ArrayList<>();
        alpha = Math.random()*1;
        beta = Math.random()*1;
        threshold = Math.random()*1;



        for (int i = 0; i < dimension; i++) {
            weights.add(Math.random()*10);
        }
    }


    //  -> Obliczanie wartosci net i zwracanie wyniki funkcji aktywacji
    public int compute(List<Double> inputs){
        double net = MateeeematyczneNarzedzia.iloczynSkalarnyLicz(inputs, weights) - threshold; // -> Iloczyn skalarny wejść i wag minus próg aktywacji
        if (net >= 0)
            return 1;
        return 0;
    }


    public void learn(List<Double> inputs, int decision){
        int wyjsciePerceptron = compute(inputs);  // -> obliczenie wyjscia perceptronu
        int blad = decision - wyjsciePerceptron; // -> obliczenie błedu perceptronu


        for (int i = 0; i < dimension; i++) {
            weights.set(i, weights.get(i)+blad*alpha* inputs.get(i));   // -> trzeba jeszcze pomnozyc przez wspolczynnik uczenia dla wag (ALPHA)

        // weights.get(i) pobiera wartosc wejsciowa dla itej cechy
        // alpha * error * inputs.get(i) oblicza wartość korekty dla danej wagi (błąd * współczynnik uczenia * wejście)
        }

        threshold -= beta * blad; // tutaj aktualizujemy prog aktywacji
    }
}

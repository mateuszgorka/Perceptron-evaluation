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
        alpha = 0.01;   // Math.random()*1;
        beta = Math.random()*1;
//        threshold = 0.5;  //Math.random()*0.5;



        for (int i = 0; i < dimension; i++) {
            weights.add(1 - Math.random());
        }
    }


    //  -> Obliczanie wartosci net i zwracanie wyniki funkcji aktywacji
    public double compute(List<Double> inputs){
        double net = MathUtils.iloczynSkalarnyLicz(inputs, weights) - threshold; // -> Iloczyn skalarny wejść i wag minus próg aktywacji
        if (net >= 0)
            return 1.0;
        return 0.0;
    }


    public void learn(List<Double> inputs, double decision){
        double wyjsciePerceptron = compute(inputs);  // -> obliczenie wyjscia perceptronu
        double blad = decision - wyjsciePerceptron; // -> obliczenie błedu perceptronu


        for (int i = 0; i < dimension; i++) {
            weights.set(i, weights.get(i)+blad*alpha* inputs.get(i));   // -> trzeba jeszcze pomnozyc przez wspolczynnik uczenia dla wag (ALPHA)

        // weights.get(i) pobiera wartosc wejsciowa dla itej cechy
        // alpha * error * inputs.get(i) oblicza wartość korekty dla danej wagi (błąd * współczynnik uczenia * wejście)
        }


        threshold -= alpha * blad; // tutaj aktualizujemy prog aktywacji  gdy blad = 0 to sie nie aktualizuje po prostu
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perceptron {


    List<Double> weights;   // -> lista wag
    double threshold;       // -> próg aktywacji
    double alpha;           // -> współczynnik uczenia dla wag
    double beta;            // ->           -||-       dla progu aktywacji
    int dimension;  //  dopuszczalna dlugosc wektora wag/wejsc

    double bias;


    // ->
    double learningRate;

    public Perceptron(int dimension, double learningRate) {
        this.dimension = dimension;
        this.learningRate = learningRate;
        weights = new ArrayList<>();
        alpha = 0.01;   // Math.random()*1;
        beta = Math.random()*1;
        //        threshold = 0.5;  //Math.random()*0.5;

        Random rand = new Random();
        this.bias = (rand.nextDouble() - 0.5) * 0.1;

        for (int i = 0; i < dimension; i++) {
            weights.add(1 - Math.random());
        }
    }


    //  -> Obliczanie wartosci net i zwracanie wyniki funkcji aktywacji
    public double compute(List<Double> inputs){
//        double net = MathUtils.iloczynSkalarnyLicz(inputs, weights) - threshold; // -> Iloczyn skalarny wejść i wag minus próg aktywacji
//
//
//        if (net >= 0)
//            return 1.0;
//        return 0.0;


        // -> trzeba takze sprawdzic czy inputs.size() != dimension _. wtedy rzucamy wyjatek


        double netVal = 0;
        for (int i = 0; i < dimension; i++) {
            netVal += inputs.get(i) * weights.get(i);  // - mnożymy każdą cechę wejściową przez jej odpowiadającą wagę
        }
        netVal += bias;                 // bias przesuwa wartosc
        return netVal;                  // -> tutaj obliczamy f(net)

    }



    public void learn(List<Double> inputs, double error){

        // -> trzeba takze sprawdzic czy inputs.size() != dimension _. wtedy rzucamy wyjatek

        for (int i = 0; i < dimension; i++) {
            double deltaW = learningRate * error * inputs.get(i);
            weights.set(i, weights.get(i) + deltaW);
        }

        double deltaBias = learningRate * error * 1.0;    // -> tutaj aktualizujemy biasy (waga jak dls wejscia x0 = 1)
        bias += deltaBias;

    }

    // Getter wag (debugowansko)

    public List<Double> getWeights() {
        return weights;
    }

    // Getter biasu
    public double getBias() {
        return bias;
    }




    public void learnOLD(List<Double> inputs, double decision){
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
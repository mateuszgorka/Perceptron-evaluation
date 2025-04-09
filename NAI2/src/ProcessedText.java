import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcessedText {



    public static List<Double> textVector(String text) {


        double[] counter = new double[26]; // -> licznik a-z
        text = text.toLowerCase();


        for (char c : text.toCharArray()) {

            if (c >= 'a' && c <= 'z') {
                counter[c - 'a']++;
            }
            // -> elo elo pomijamy polskie znaki (ignorujemy tazke spacje, liczby interpunkcje)

        }

        // teraz norma
        double norm = 0;

        for (double v : counter) {
            norm += v * v;      //    norma eukildesowa   ||v|| = sqrt(v₁² + v₂² + ... + v₂₆²))
        }
        norm = Math.sqrt(norm);

        List<Double> vector = new ArrayList<>(26);

        if (norm > 0) {
            for (double v : counter) {
                vector.add(v / norm);
            }
        } else {
            vector.addAll(Collections.nCopies(26,0.0));   // -> Jeśli norma == 0 (naaaa przyklad gdy tekst nie zawiera żadnej litery a-z), zwracany jest wektor zerowy ([0.0, 0.0, ..., 0.0]).
        }



        return vector;
    }





}

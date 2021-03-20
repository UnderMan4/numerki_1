import java.util.HashMap;
import java.util.Map;

public class Secant {

    public static Map<String, Number> secant(Function fun, double x_g1, double x_g2, double war, boolean kryt) {

        // Jeżeli kryt == true to uzywamy kryterium stopu bazujacego na epsilonie, jezeli false, to na iteracjach

        double y_g1 = fun.calculate(x_g1);
        double y_g2 = fun.calculate(x_g2);
        int iter = 0;

        double a, b, x0 = 0, y0, epsi, prev_x = 0;
        boolean stop = false;

        HashMap<String, Number> values = new HashMap<>();

        do {

            if (kryt) {
                prev_x = x0;
            }

            a = (y_g2 - y_g1) / (x_g2 - x_g1);
            b = y_g1 - (a * x_g1);

            if (a == 0) {
                values.put("iterations", iter);
                values.put("result", 0);

                return values;
            }

            x0 = (0 - b) / a;
            y0 = (a * x0) + b;

            if ((y0 * y_g1) > 0) {
                x_g2 = x0;
                y_g2 = fun.calculate(x_g2);
            } else {
                x_g1 = x0;
                y_g1 = fun.calculate(x_g1);
            }

            if (x0 == 0) {
                stop = true;
            } else {
                if (kryt) {
                    epsi = prev_x - x0;
                    if (epsi < 0) {
                        epsi = 0 - epsi;
                    }
                    if (epsi < war) {
                        stop = true;
                    }
                } else {
                    war--;
                    if (war <= 0) {
                        stop = true;
                    }
                }
            }
            iter++;
        } while (stop != true);

        values.put("iterations", iter);
        values.put("result", x0);

        return values;
    }

}

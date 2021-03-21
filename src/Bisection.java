import java.util.HashMap;
import java.util.Map;

public class Bisection {
    public static Map<String, Number> bisection(Function function, double x1, double x2, double condition, boolean accuracy){
        
        if((function.calculate(x1)* function.calculate(x2) > 0) || (x1 == x2)){
            System.out.println("Podano nieprawidłowe granice przedziału");
            return null;
        }
        
        if (x1 > x2){
            double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        
        boolean stop = false;
        double x0;
        int step = 0;
        Map<String, Number> values = new HashMap<>();
        
        if (function.calculate((x1+x2)/2) == 0){
            values.put("iterations", step);
            values.put("result", (x1 + x2)/2);
            return values;
        }
        

        do {
            x0 = (x1 + x2)/2;
//            System.out.printf("%.4f   %.4f   %.4f \n", x1, x0, x2);
            
            if (function.calculate(x0) > 0){
                x2 = x0;
            } else {
                x1 = x0;
            }
//            System.out.println(x0);
            step++;
            if ((accuracy && (Math.abs(x1-x2) < condition)) || (!accuracy && (step == (int)condition))){
                stop = true;
            }
        } while (!stop);
        System.out.println("Liczba iteracji: " + step);
        values.put("iterations", step);
        values.put("result", (x1 + x2)/2);
        
        return values;
    }
}

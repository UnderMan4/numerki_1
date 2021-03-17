public class App {

    private static Function function_a = (x) -> {
        return 0.5*x*x*x - 3*x*x + 5*x + 3;
    };
    private static Function function_b = (x) -> {
        return Math.pow(5, x);
    };
    private static Function function_c = (x) -> {
        return Math.sin(x);
    };

    public static void main(String args[]) {
        System.out.println("Prosze państwa, tutaj powstanie coś pięknego");
        System.out.println(function_b.calculate(1.5));
    }

}

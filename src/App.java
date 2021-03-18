import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.plot.FunctionPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;

public class App {

    private static Function function_a = (x) -> {
        return 0.5*x*x*x - 3*x*x + 5*x + 3;
    };
    private static Function function_b = (x) -> {
        return Math.pow(5, x) - 0.5;
    };
    private static Function function_c = (x) -> {
        return Math.cos(x);
    };

    private static void plot(String fun, double x0, double x_g1, double x_g2) {
        JavaPlot p = new JavaPlot(false);

        PlotStyle zeroStyle = new PlotStyle();
        zeroStyle.setStyle(Style.LINES);
        zeroStyle.setLineType(NamedPlotColor.BLACK);
        zeroStyle.setLineWidth(2);
        FunctionPlot zeroLine = new FunctionPlot("0");
        zeroLine.setPlotStyle(zeroStyle);
        p.addPlot(zeroLine);

        PlotStyle lineStyle = new PlotStyle();
        lineStyle.setStyle(Style.LINES);
        lineStyle.setLineType(NamedPlotColor.BLUE);
        lineStyle.setLineWidth(4);
        FunctionPlot functionLine = new FunctionPlot(fun);
        functionLine.setPlotStyle(lineStyle);
        p.addPlot(functionLine);
        p.getAxis("x").setBoundaries(x_g1, x_g2);

        PlotStyle pointStyle = new PlotStyle();
        pointStyle.setStyle(Style.DOTS);
        pointStyle.setLineType(NamedPlotColor.RED);
        pointStyle.setLineWidth(10);
        double[][] plot = {{x0, 0}};
        DataSetPlot points = new DataSetPlot(plot);
        points.setPlotStyle(pointStyle);
        p.addPlot(points);

        p.set("nokey", "");
        p.plot();
    }

    public static void main(String args[]) {

        double x_g1 = -5, x_g2 = 1;
        double x0_sieczna_a = Sieczna.sieczna(function_b,-5, 1, 0.0001, true);
        plot("0.5*x**3 - 3*x**2 + 5*x + 3", x0_sieczna_a, x_g1, x_g2);

    }

}


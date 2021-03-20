import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.plot.FunctionPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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

    private static void plot(Function fun, double x0Bisection, double x0Secant, double x_g1, double x_g2, boolean bisection) {
        JavaPlot p = new JavaPlot(false);

        // Oznaczamy na wykresie os OX
        PlotStyle zeroStyle = new PlotStyle();
        zeroStyle.setStyle(Style.LINES);
        zeroStyle.setLineType(NamedPlotColor.BLACK);
        zeroStyle.setLineWidth(2);
        FunctionPlot zeroLine = new FunctionPlot("0");
        zeroLine.setPlotStyle(zeroStyle);
        p.addPlot(zeroLine);
        
//        PlotStyle intervalStyle = new PlotStyle();
//        intervalStyle.setStyle(Style.FSTEPS);
//        intervalStyle.setLineType(NamedPlotColor.AQUAMARINE);
//        intervalStyle.setLineWidth(2);
//        FunctionPlot intervalLine = new FunctionPlot();

        // Ustawia parametry reprezentacji graficznej funkcji
        PlotStyle lineStyle = new PlotStyle();
        lineStyle.setStyle(Style.LINES);
        lineStyle.setLineType(NamedPlotColor.BLUE);
        lineStyle.setLineWidth(4);
        // Oblicza zbiot punktow na bazie ktorego zarysowywana bedzie funkcja
        double[][] functionPoints = new double[100][2];
        int i = 0;
        for (double d = x_g1; d < x_g2 && i < 100; d += (x_g2 - x_g1)/100) {
            functionPoints[i][0] = d;
            functionPoints[i][1] = fun.calculate(d);
            i++;
        }
        DataSetPlot functionLine = new DataSetPlot(functionPoints);
        functionLine.setPlotStyle(lineStyle);
        p.addPlot(functionLine);
        p.getAxis("x").setBoundaries(x_g1, x_g2);

        PlotStyle pointStyleBisection = new PlotStyle();
        pointStyleBisection.setStyle(Style.DOTS);
        pointStyleBisection.setLineType(NamedPlotColor.RED);
        pointStyleBisection.setLineWidth(10);
        
        PlotStyle pointStyleSecant = new PlotStyle();
        pointStyleSecant.setStyle(Style.DOTS);
        pointStyleSecant.setLineType(NamedPlotColor.GREEN);
        pointStyleSecant.setLineWidth(10);
        // Zarysowuje miejsce zerowe dla bisekcji
        if (bisection){
            double[][] plotBisection = {{x0Bisection, 0}};
            DataSetPlot pointsBisection = new DataSetPlot(plotBisection);
            pointsBisection.setPlotStyle(pointStyleBisection);
            p.addPlot(pointsBisection);
        }
        
        
        // Zarysowuje miejsce zerowe dla siecznej
        double[][] plotSecant = {{x0Secant, 0}};
        DataSetPlot pointsSecant = new DataSetPlot(plotSecant);
        pointsSecant.setPlotStyle(pointStyleSecant);
        p.addPlot(pointsSecant);

        // Zmienia legende na niewidoczna
        p.set("nokey", "");
        p.plot();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    private static void startButton(ButtonGroup functionGroup, JTextField intervalStart, 
                                    JTextField intervalEnd, ButtonGroup criterionGroup, 
                                    JTextField criterionText, JFrame frame){
        System.out.println("\n\n\n");
        System.out.println(functionGroup.getSelection().getActionCommand());
        System.out.println(intervalStart.getText());
        System.out.println(intervalEnd.getText());
        System.out.println(criterionGroup.getSelection());
        System.out.println(criterionText.getText());
        
        String intervalStartString = intervalStart.getText();
        String intervalEndString = intervalEnd.getText();
        String criterionString = criterionText.getText();
        Boolean bisectionMethod = true;
        
        if((functionGroup.getSelection()==null) || (intervalStartString=="") 
                || (intervalEndString=="") || (criterionGroup.getSelection()==null)
                || (criterionString=="")){
            JOptionPane.showMessageDialog(frame, "Wypełnij wszystkie pola");
            return;
        }
        
        if (!isNumeric(intervalStartString) || !isNumeric(intervalEndString)
                || !isNumeric(criterionString)){
            JOptionPane.showMessageDialog(frame, "Podaj prawidłowe wartości");
            return;
        }
        
        double intervalStartDouble = Double.parseDouble(intervalStartString);
        double intervalEndDouble = Double.parseDouble(intervalEndString);
        
        if (intervalStartDouble == intervalEndDouble){
            JOptionPane.showMessageDialog(frame, "Podano nieprawidłowy przedział");
            return;
        }
        
        char selectedFunction = functionGroup.getSelection().getActionCommand().toCharArray()[0];
        char selectedCriterion = criterionGroup.getSelection().getActionCommand().toCharArray()[0];

        Map<Character, Function> temp = new HashMap<>();
        temp.put('A', function_a);
        temp.put('B', function_b);
        temp.put('C', function_c);


        if (temp.get(selectedFunction).calculate(intervalStartDouble)* temp.get(selectedFunction).calculate(intervalEndDouble) > 0){
            JOptionPane.showMessageDialog(frame, "Program nie będzie w stanie wykonać obliczeń\nmetodą bisekcji dla zadanego przedziału.\nKliknij OK aby kontynuować");
            bisectionMethod = false;
        }

        double criterionDouble = Double.parseDouble(criterionString);
        
        Map<String, Number> bisectionResult = null;
        Map<String, Number> secantResult = null;

        if (selectedCriterion == 'A') {
            bisectionResult = Bisection.bisection(temp.get(selectedFunction), intervalStartDouble, intervalEndDouble, criterionDouble, true);
            secantResult = Secant.secant(temp.get(selectedFunction), intervalStartDouble, intervalEndDouble, criterionDouble, true);
        } else {
            bisectionResult = Bisection.bisection(temp.get(selectedFunction), intervalStartDouble, intervalEndDouble, criterionDouble, false);
            secantResult = Secant.secant(temp.get(selectedFunction), intervalStartDouble, intervalEndDouble, criterionDouble, false);
        }
        String message = "";
        if (bisectionMethod){
            message = String.format("""
                            METODA BISEKCJI (czerwony)
                            liczba iteracji: %d
                            wynik: %.3f
                            
                            METODA SIECZNYCH (zielony)
                            liczba iteracji: %d 
                            wynik: %.3f""", 
                    bisectionResult.get("iterations").intValue(), bisectionResult.get("result").doubleValue(), 
                    secantResult.get("iterations").intValue(), secantResult.get("result").doubleValue());
        } else {
            message = String.format("""
                            METODA BISEKCJI (czerwony)
                            liczba iteracji: brak
                            wynik: brak
                            
                            METODA SIECZNYCH (zielony)
                            liczba iteracji: %d
                            wynik: %.3f""", 
                    secantResult.get("iterations").intValue(), secantResult.get("result").doubleValue());
        }
        
        if (secantResult.get("status").intValue() == 1){
            message = message + """
                            
                            
                            UWAGA!  Na zadanym przedziale poprowadzona 
                            sieczna w trakcie rozwiązywania przyjęła współczynnik 
                            kierunkowy a=0. W związku z tym otrzymany wynik 
                            może nie być zgodny z prawdą.""";
        }
        
        double intervalSize = Math.abs(intervalStartDouble - intervalEndDouble);
        if (bisectionMethod){
            plot(temp.get(selectedFunction), bisectionResult.get("result").doubleValue(), 
                    secantResult.get("result").doubleValue(), intervalStartDouble - intervalSize/10, 
                    intervalEndDouble + intervalSize/10, true);
        } else {
            plot(temp.get(selectedFunction), 0
                    , secantResult.get("result").doubleValue(), intervalStartDouble - intervalSize/10, 
                    intervalEndDouble + intervalSize/10, false);
        }
        JOptionPane.showMessageDialog(frame, message);
    }
    

    public static void main(String args[]) {

        double x_g1 = -5, x_g2 = 1;
        double x0_sieczna_a = (double) Secant.secant(function_b,-5, 1, 0.0001, true).get("result");
//        plot(function_b, x0_sieczna_a, x_g1, x_g2);
//        double bisection_a = Bisection.bisection(function_a, -2, 2, 0.0001, true);
//        System.out.printf("%.10f", bisection_a);
        
        JFrame frame = new JFrame("Metody numeryczne - zadanie 1");
        JLabel labelFunctions = new JLabel("Wybierz funkcję:");
//        JLabel labelMethods = new JLabel("Wybierz metodę:");
        JLabel labelInterval = new JLabel("Podaj przedział:");
        JLabel labelIntervalStart = new JLabel("x₁:");
        JLabel labelIntervalEnd = new JLabel("x₂:");
        JLabel labelCriterionA = new JLabel("Wybierz kryterium zatrzymania i");
        JLabel labelCriterionB = new JLabel("podaj warunek:");
        labelFunctions.setBounds(25, 5, 140, 30);
//        labelMethods.setBounds(205, 5, 130, 30);
        labelInterval.setBounds(205, 5, 150, 30);
        labelIntervalStart.setFont(new Font("Dialog", Font.PLAIN, 13));
        labelIntervalEnd.setFont(new Font("Dialog", Font.PLAIN, 13));
        labelIntervalStart.setBounds(205, 35, 50, 30);
        labelIntervalEnd.setBounds(205, 70, 50, 30);
        labelCriterionA.setBounds(385, 0, 200, 30);
        labelCriterionB.setBounds(385, 15, 200, 30);
//        System.out.println(labelIntervalEnd.getFont());
        
        JRadioButton radioFunctionA = new JRadioButton("f(x)=0.5x³-3x²+5x+3");
        JRadioButton radioFunctionB = new JRadioButton("f(x)=5^x-0.5");
        JRadioButton radioFunctionC = new JRadioButton("f(x)=cos(x)");
        radioFunctionA.setBounds(20, 30, 140, 30);
        radioFunctionB.setBounds(20, 60, 140, 30);
        radioFunctionC.setBounds(20, 90, 140, 30);
        radioFunctionA.setActionCommand("A");
        radioFunctionB.setActionCommand("B");
        radioFunctionC.setActionCommand("C");
        ButtonGroup radioFunctions = new ButtonGroup();
        radioFunctions.add(radioFunctionA);
        radioFunctions.add(radioFunctionB);
        radioFunctions.add(radioFunctionC);
        
//        JRadioButton radioMethodA = new JRadioButton("metoda bisekcji");
//        JRadioButton radioMethodB = new JRadioButton("metoda stycznych");
//        radioMethodA.setBounds(200, 30, 130, 30);
//        radioMethodB.setBounds(200, 60, 130, 30);
//        ButtonGroup radioMethods = new ButtonGroup();
//        radioMethods.add(radioMethodA);
//        radioMethods.add(radioMethodB);
        
        JTextField textIntervalStart = new JTextField();
        JTextField textIntervalEnd = new JTextField();
        textIntervalStart.setBounds(225, 35, 100, 30);
        textIntervalEnd.setBounds(225, 70, 100, 30);
        
        JRadioButton radioCriterionA = new JRadioButton("dokładność");
        JRadioButton radioCriterionB = new JRadioButton("liczba iteracji");
        ButtonGroup radioCriterion = new ButtonGroup();
        radioCriterion.add(radioCriterionA);
        radioCriterion.add(radioCriterionB);
        JTextField textCriterion = new JTextField();
        radioCriterionA.setBounds(380, 40, 100, 30);
        radioCriterionB.setBounds(380, 70, 100, 30);
        radioCriterionA.setActionCommand("A");
        radioCriterionB.setActionCommand("B");
        textCriterion.setBounds(495, 55, 100, 30);
        
        JButton buttonStart = new JButton("Policz");
        buttonStart.setBounds(245, 140, 150, 30);
        
        
        frame.add(labelFunctions);
        frame.add(radioFunctionA);
        frame.add(radioFunctionB);
        frame.add(radioFunctionC);
        
//        frame.add(labelMethods);
//        frame.add(radioMethodA);
//        frame.add(radioMethodB);
        
        frame.add(labelInterval);
        frame.add(labelIntervalStart);
        frame.add(textIntervalStart);
        frame.add(labelIntervalEnd);
        frame.add(textIntervalEnd);
        
        frame.add(labelCriterionA);
        frame.add(labelCriterionB);
        frame.add(radioCriterionA);
        frame.add(radioCriterionB);
        frame.add(textCriterion);
        
        frame.add(buttonStart);
        
        frame.setSize(640, 230);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        
        
        buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                startButton(radioFunctions, textIntervalStart, 
                        textIntervalEnd, radioCriterion, 
                        textCriterion, frame);
            }
        });
    }

}


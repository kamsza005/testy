package aco;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.QuickChart;

import java.util.ArrayList;
import java.util.List;

public class AcoTester {
    private AcoProperty acoProperty;
    private Double start;
    private Double step;
    private Double stop;


    public AcoTester(AcoProperty acoProperty, Double start, Double step, Double stop) {
        this.acoProperty = acoProperty;
        this.start = start;
        this.step = step;
        this.stop = stop;
    }

    public void test() {
        List<AcoTestResult> acoTestResults = new ArrayList<>();
        for (double i = start; i < stop; i+=step) {
            System.out.println("Current: " + i + "    Stop: " + stop);
            try {
                AcoTest acoTest = new AcoTest(acoProperty, i);
                acoTestResults.add(new AcoTestResult(i, acoTest.solve()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Current: " + stop + "    Stop: " + stop);
        try {
            AcoTest acoTest = new AcoTest(acoProperty, stop);
            acoTestResults.add(new AcoTestResult(stop, acoTest.solve()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        getChart(acoTestResults);
    }

    private void getChart(List<AcoTestResult> acoTestResults) {
        //tu będzie generowany wykres
        int size = acoTestResults.size();
        double[] xData = new double[size];
        double[] yData = new double[size];
        double[] avgData = new double[size];
        double sum = 0.0;
        double avg;

        int i = 0;
        for (AcoTestResult ats : acoTestResults) {
            xData[i] = ats.getValue();
            yData[i] = ats.getScore();
            sum += ats.getScore();
            avg = sum / (i + 1);
            avgData[i] = avg;
            i++;
        }


        XYChart xyChart = QuickChart.getChart("Wpływ " + acoProperty.toString(), acoProperty.toString(), "Score","Score" , xData, yData);
        xyChart.addSeries("Avg score", xData, avgData);
        new SwingWrapper(xyChart).displayChart();
    }
}

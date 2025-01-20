package io.github.cakilgan;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.joml.Math;

import javax.swing.*;

public class Test {
    static float _temp_a = 0.37f;
    static float _temp_c = 25f;
    static float calc(float x,float B,float add){
        return (float) (((_temp_a*x)+_temp_c)*Math.sin(Math.toRadians(B*x+add))+3000);
    }
    static float calc_stable(float x, float B, float add) {
        float amplitude = 3000;
        return (float) (amplitude * Math.sin(Math.toRadians(B * x + add)) + 3000);
    }
    static void loop(XYSeries phase1,float var1){
        int totalSteps = 10000;
        float freq = 0f;
        for (int step = 0; step <= totalSteps; step++) {
            float value;
            value = calc(step,freq,var1);
            if (step>8000){
                value = calc_stable(step,freq,var1);
            }else{
            }
                freq+= 0.0002f;
            phase1.add(step, value);
            System.out.println("freq: "+freq+" val: "+value+" step:"+step);
        }
    }
    public static void main(String[] args) {
        XYSeries phase1 = new XYSeries("Channel 0");
        XYSeries phase2 = new XYSeries("Channel 1");
        XYSeries phase3 = new XYSeries("Channel 2");

        loop(phase1,0);
        loop(phase2,120);
        loop(phase3,240);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(phase1);
        dataset.addSeries(phase2);
        dataset.addSeries(phase3);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Channel 0 İlk 8000 Değer",
                "Index",
                "Channel 0 Değeri",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        JFrame frame = new JFrame("Channel 0 Grafiği");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}

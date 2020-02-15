package com.ledinh.androidstockchart.math;

import com.ledinh.androidstockchart.chart.Kline;
import com.ledinh.androidstockchart.chart.KlinesSet;
import com.ledinh.androidstockchart.chart.RSI;
import com.ledinh.androidstockchart.chart.RSISet;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    public static double simpleMovingAverage(float[] values) {
        float sum = 0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }

        float average = sum / values.length;

        return average;
    }

    public static RSISet rsi(KlinesSet klinesSet, int smoothingPeriod) {
        List<Kline> klines = klinesSet.getValues();
        List<RSI> values = new ArrayList<>();
        double rsiMaxEma = 0;
        double rsiABSEma = 0;
        for (int i = 0; i < klines.size(); i++) {
            Kline kline = klines.get(i);
            RSI rsi = new RSI();

            rsi.closeTime = kline.closeTime;
            rsi.openTime =  kline.openTime;

            if (i == 0) {
                rsi.rsi = 0;
            }
            else {
                double Rmax = Math.max(0, kline.close - klines.get(i - 1).close);
                double RAbs = Math.abs(kline.close - klines.get(i - 1).close);
                rsiMaxEma = (Rmax + (smoothingPeriod - 1) * rsiMaxEma) / smoothingPeriod;
                rsiABSEma = (RAbs + (smoothingPeriod - 1) * rsiABSEma) / smoothingPeriod;

                rsi.rsi = (rsiMaxEma / rsiABSEma) * 100;
            }

            values.add(rsi);
        }

        return new RSISet(values, klinesSet.getUnit(), smoothingPeriod);
    }
}

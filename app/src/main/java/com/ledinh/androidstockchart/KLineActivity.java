package com.ledinh.androidstockchart;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.DimenRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.ledinh.androidstockchart.chart.KLineChartView;
import com.ledinh.androidstockchart.chart.Kline;
import com.ledinh.androidstockchart.chart2.DrawingRSIChart;
import com.ledinh.androidstockchart.chart2.KlinesSet;
import com.ledinh.androidstockchart.chart2.RSISet;
import com.ledinh.androidstockchart.chart2.DrawingKlineChart;
import com.ledinh.androidstockchart.chart2.TimeUnit;
import com.ledinh.androidstockchart.chart2.Chart;
import com.ledinh.androidstockchart.math.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class KLineActivity extends AppCompatActivity {

//    KLineChartView kLineChartView;
//    RSIChartView rsiChartView;
    Chart chart;

    private float getDimension(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline);

//        kLineChartView = findViewById(R.id.kchart_view);
//        rsiChartView = findViewById(R.id.rsichart_view);

        chart = findViewById(R.id.chart2);

        final List<Kline> klines = readFileSample();

        chart.setTimeUnit(TimeUnit.ONE_DAY);
        chart.setMaxIndex(klines.size() - 1);
        chart.setLastDate(klines.get(klines.size() - 1).openTime);
        chart.setScreenDataCount(60);

        chart.setBackgroundColor(ContextCompat.getColor(this, R.color.chart_background));
        chart.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        chart.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        chart.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        chart.setViewSeparatorColor(ContextCompat.getColor(this, R.color.chart_view_separator));
        chart.setTextYearSize((int) getDimension(R.dimen.chart_text_year_size));
        chart.setTextYearColor(ContextCompat.getColor(this, R.color.chart_text_year));

        final DrawingKlineChart klineDrawing = new DrawingKlineChart(chart);
        klineDrawing.setDecreasingColor(ContextCompat.getColor(this, R.color.chart_red));
        klineDrawing.setIncreasingColor(ContextCompat.getColor(this, R.color.chart_green));
        klineDrawing.setKlineWidth(getDimension(R.dimen.chart_kline_width));
        klineDrawing.setKlineInnerLineWidth(getDimension(R.dimen.chart_kline_inner_width));
        klineDrawing.setSelectedKlineColor(ContextCompat.getColor(this, R.color.chart_yellow));
        klineDrawing.setSelectedKlineLineWidth(getDimension(R.dimen.chart_selector_line_width));
        klineDrawing.setAutoScale(true);
        klineDrawing.setWeight(2);

        final DrawingRSIChart drawingRSI = new DrawingRSIChart(chart);
        drawingRSI.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi1));
        drawingRSI.setLineWidth(getDimension(R.dimen.chart_kline_inner_width));
        drawingRSI.setAutoScale(true);
        drawingRSI.setWeight(1);
//        drawingRSI.setAxisMin(0);
//        drawingRSI.setAxisMax(100);


        final DrawingRSIChart drawingRSI2 = new DrawingRSIChart(chart);
        drawingRSI2.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi2));
        drawingRSI2.setLineWidth(getDimension(R.dimen.chart_kline_inner_width));
        drawingRSI2.setAutoScale(false);
        drawingRSI2.setAxisMin(0);
        drawingRSI2.setAxisMax(100);
        drawingRSI2.setWeight(1);

        chart.addDrawingElement(klineDrawing);
        chart.addDrawingElement(drawingRSI);
        chart.addDrawingElement(drawingRSI2);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KlinesSet klinesSet = new KlinesSet(klines, TimeUnit.ONE_DAY);
                RSISet rsiSet1 = Calculator.rsi(klinesSet, 5);
                RSISet rsiSet2 = Calculator.rsi(klinesSet, 14);

                klineDrawing.setChartData(klinesSet);
                chart.invalidate();

                drawingRSI.setChartData(rsiSet1);
                chart.invalidate();

                drawingRSI2.setChartData(rsiSet2);
                chart.invalidate();


//                KlinesSet klinesSet = new KlinesSet(klines, TimeUnit.ONE_DAY);
//                kLineChartView.setKlinesSet(klinesSet);
//                kLineChartView.invalidate();
//
//                klineDrawing.setKlinesSet(klinesSet);
//                chart.invalidate();
//
//                RSISet rsiSet1 = Calculator.rsi(klinesSet, 5);
//                RSISet rsiSet2 = Calculator.rsi(klinesSet, 9);
//                RSISet rsiSet3 = Calculator.rsi(klinesSet, 14);

//                rsiChartView.addRsiSet(rsiSet1);
//                rsiChartView.addRsiSet(rsiSet2);
//                rsiChartView.addRsiSet(rsiSet3);

                Log.d("KLineActivity", "klines size = " + klines.size());

            }
        });
    }

    public List<Kline> readFileSample() {
        StringBuilder s = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("sample_binance")));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                s.append(mLine);
            }
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Kline.class, new KlineDeserializer());
        Gson gson = gsonBuilder.create();

        final List<Kline> data = gson.fromJson(s.toString(), new TypeToken<List<Kline>>() {}.getType());

        return data;
    }

    class KlineDeserializer implements JsonDeserializer<Kline>
    {

        @Override
        public Kline deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray jsonArray = json.getAsJsonArray();

            long openTime = jsonArray.get(0).getAsLong();
            double open = jsonArray.get(1).getAsDouble();
            double high = jsonArray.get(2).getAsDouble();
            double low = jsonArray.get(3).getAsDouble();
            double close = jsonArray.get(4).getAsDouble();
            double volume = jsonArray.get(5).getAsDouble();
            long closeTime = jsonArray.get(6).getAsLong();
            double quoteAssetVolume = jsonArray.get(7).getAsDouble();
            long numberOfTrades = jsonArray.get(8).getAsLong();
            double takerBuyBaseAssetVolume = jsonArray.get(9).getAsDouble();
            double takerBuyQuoteAssetVolume = jsonArray.get(10).getAsDouble();
            double ignore = jsonArray.get(11).getAsDouble();

            return new Kline(openTime, open, high, low, close, volume, closeTime);
        }
    }
}

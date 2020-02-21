package com.ledinh.androidstockchart;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.ViewTreeObserver;

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
import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.model.Kline;
import com.ledinh.androidstockchart.chart.util.Viewport;
import com.ledinh.androidstockchart.chart.view.ChartView2;
import com.ledinh.androidstockchart.chart.view.ChartViewFragment;
import com.ledinh.androidstockchart.chart.view.element.DrawingArea;
import com.ledinh.androidstockchart.chart.view.element.DrawingElement;
import com.ledinh.androidstockchart.chart.view.element.DrawingRSIChart;
import com.ledinh.androidstockchart.chart.set.KlinesSet;
import com.ledinh.androidstockchart.chart.set.RSISet;
import com.ledinh.androidstockchart.chart.view.element.DrawingKlineChart;
import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.math.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class KLineActivity2 extends AppCompatActivity {

    ChartView2 chartView;

    private float getDimension(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline2);

        chartView = findViewById(R.id.chart2);

        ////////////////////// READ DATA //////////////////////
        final List<Kline> klines = readFileSample();
        ////////////////////// END READ DATA //////////////////////

        ////////////////////// AXIS //////////////////////
        final YAxis yAxisLeftKline = new YAxis();
        yAxisLeftKline.setPosition(YAxis.Position.LEFT);
        yAxisLeftKline.setGridRows(4);
        yAxisLeftKline.setUnit("Dollar");
        yAxisLeftKline.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        yAxisLeftKline.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftKline.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
        yAxisLeftKline.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftKline.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
        yAxisLeftKline.setLeftPadding((int) getDimension(R.dimen.chart_axis_padding));


        final YAxis yAxisLeftRSI = new YAxis();
        yAxisLeftRSI.setPosition(YAxis.Position.LEFT);
        yAxisLeftRSI.setGridRows(4);
        yAxisLeftRSI.setUnit("%");
        yAxisLeftRSI.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        yAxisLeftRSI.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftRSI.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
        yAxisLeftRSI.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftRSI.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
        yAxisLeftRSI.setLeftPadding((int) getDimension(R.dimen.chart_axis_padding));

        ////////////////////// END AXIS //////////////////////

        ////////////////////// DRAWING AREA //////////////////////
        DrawingArea drawingAreaKline = new DrawingArea();
        drawingAreaKline.setLeftAxis(yAxisLeftKline);
        DrawingArea drawingAreaRSI = new DrawingArea();
        drawingAreaRSI.setLeftAxis(yAxisLeftRSI);
        ////////////////////// DRAWING AREA //////////////////////


        ////////////////////// CHART VIEW FRAGMENT //////////////////////
        final ChartViewFragment chartViewFragmentKline = new ChartViewFragment();
        chartViewFragmentKline.setDrawingArea(drawingAreaKline);
        chartViewFragmentKline.getDrawingArea().setViewport(new Viewport());
        chartViewFragmentKline.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        chartViewFragmentKline.setAutoScale(true);

        final ChartViewFragment chartViewFragmentRSI = new ChartViewFragment();
        chartViewFragmentRSI.setDrawingArea(drawingAreaRSI);
        chartViewFragmentRSI.getDrawingArea().setViewport(new Viewport());
        chartViewFragmentRSI.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        chartViewFragmentRSI.setAutoScale(false);

        final DrawingKlineChart drawingKlineChart = new DrawingKlineChart(chartViewFragmentKline);
        drawingKlineChart.setDecreasingColor(ContextCompat.getColor(this, R.color.chart_red));
        drawingKlineChart.setIncreasingColor(ContextCompat.getColor(this, R.color.chart_green));
        drawingKlineChart.setKlineWidth(getDimension(R.dimen.chart_kline_width));
        drawingKlineChart.setKlineInnerLineWidth(getDimension(R.dimen.chart_kline_inner_width));
        chartViewFragmentKline.addDrawingElement(drawingKlineChart);

        final DrawingRSIChart drawingRSI5Chart = new DrawingRSIChart(chartViewFragmentRSI);
        drawingRSI5Chart.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi1));
        drawingRSI5Chart.setLineWidth(getDimension(R.dimen.chart_rsi_line_width));

        final DrawingRSIChart drawingRSI9Chart = new DrawingRSIChart(chartViewFragmentRSI);
        drawingRSI9Chart.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi2));
        drawingRSI9Chart.setLineWidth(getDimension(R.dimen.chart_rsi_line_width));

        final DrawingRSIChart drawingRSI14Chart = new DrawingRSIChart(chartViewFragmentRSI);
        drawingRSI14Chart.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi3));
        drawingRSI14Chart.setLineWidth(getDimension(R.dimen.chart_rsi_line_width));

        chartViewFragmentRSI.addDrawingElement(drawingRSI5Chart);
        chartViewFragmentRSI.addDrawingElement(drawingRSI9Chart);
        chartViewFragmentRSI.addDrawingElement(drawingRSI14Chart);
        ////////////////////// END CHART VIEW FRAGMENT //////////////////////

        chartView.addChartViewFragment(chartViewFragmentKline);
        chartView.addChartViewFragment(chartViewFragmentRSI);


        chartView.setTimeUnit(TimeUnit.ONE_DAY);
        chartView.setMaxIndex(klines.size() - 1);
        chartView.setLastDate(klines.get(klines.size() - 1).openTime);
        chartView.setScreenDataCount(60);

        chartView.setBackgroundColor(ContextCompat.getColor(this, R.color.chart_background));
        chartView.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        chartView.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        chartView.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        chartView.setViewSeparatorColor(ContextCompat.getColor(this, R.color.chart_view_separator));
        chartView.setTextYearSize((int) getDimension(R.dimen.chart_text_year_size));
        chartView.setTextYearColor(ContextCompat.getColor(this, R.color.chart_text_year));

        chartView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                chartView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Viewport viewportChartView = chartView.getViewportChartView();
                Viewport viewport = new Viewport();
                viewport.setViewWidth(viewportChartView.getViewWidth());
                viewport.setViewHeight(viewportChartView.getViewHeight());
                viewport.setViewingPosition(new Rect(0, 0, viewportChartView.getViewWidth(), viewportChartView.getViewHeight() / 2));

                Viewport viewportRSI = new Viewport();
                viewportRSI.setViewWidth(viewportChartView.getViewWidth());
                viewportRSI.setViewHeight(viewportChartView.getViewHeight());
                viewportRSI.setViewingPosition(new Rect(0, viewportChartView.getViewHeight() / 2, viewportChartView.getViewWidth(), viewportChartView.getViewHeight()));

                chartViewFragmentKline.getDrawingArea().setViewport(viewport);
                chartViewFragmentRSI.getDrawingArea().setViewport(viewportRSI);

                chartView.invalidate();
                Log.d("KLineActivity2" , "onGlobalLayout --- chartView.getWidth " + chartView.getWidth());
                Log.d("KLineActivity2" , "onGlobalLayout --- chartView.getHeight " + chartView.getHeight());

            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KlinesSet klinesSet = new KlinesSet(klines, TimeUnit.ONE_DAY);

                yAxisLeftRSI.setAxisMin(0);
                yAxisLeftRSI.setAxisMax(100);

                RSISet rsiSet1 = Calculator.rsi(klinesSet, 5);
                RSISet rsiSet2 = Calculator.rsi(klinesSet, 9);
                RSISet rsiSet3 = Calculator.rsi(klinesSet, 14);

                drawingKlineChart.setChartData(klinesSet);
                drawingRSI5Chart.setChartData(rsiSet1);
                drawingRSI9Chart.setChartData(rsiSet2);
                drawingRSI14Chart.setChartData(rsiSet3);

                chartView.invalidate();

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

package com.ledinh.androidstockchart;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
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
import com.ledinh.androidstockchart.chart.view.StockChartView;
import com.ledinh.androidstockchart.chart.view.container.Chart;
import com.ledinh.androidstockchart.chart.view.element.KLineElement;
import com.ledinh.androidstockchart.chart.view.element.RSIElement;
import com.ledinh.androidstockchart.chart.view.container.Frame;
import com.ledinh.androidstockchart.chart.set.KlinesSet;
import com.ledinh.androidstockchart.chart.set.RSISet;
import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.math.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class KLineActivity2 extends AppCompatActivity {

    StockChartView stockChartView;

    private float getDimension(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline2);

        stockChartView = findViewById(R.id.chart2);

        ////////////////////// READ DATA //////////////////////
        final List<Kline> klines = readFileSample();
        ////////////////////// END READ DATA //////////////////////

        ////////////////////// AXIS //////////////////////
        final YAxis yAxisLeftKline = new YAxis();
        yAxisLeftKline.setPosition(YAxis.Position.LEFT);
        yAxisLeftKline.setGridRows(6);
        yAxisLeftKline.setUnit("Dollar");
        yAxisLeftKline.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        yAxisLeftKline.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftKline.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
        yAxisLeftKline.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftKline.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
        yAxisLeftKline.setLeftPadding((int) getDimension(R.dimen.chart_axis_padding));


        final YAxis yAxisLeftRSI = new YAxis();
        yAxisLeftRSI.setPosition(YAxis.Position.LEFT);
        yAxisLeftRSI.setGridRows(6);
        yAxisLeftRSI.setUnit("%");
        yAxisLeftRSI.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        yAxisLeftRSI.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftRSI.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
        yAxisLeftRSI.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftRSI.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
        yAxisLeftRSI.setLeftPadding((int) getDimension(R.dimen.chart_axis_padding));

        ////////////////////// END AXIS //////////////////////

        ////////////////////// DRAWING AREA //////////////////////
        Frame frameKline = new Frame();
        frameKline.setLeftAxis(yAxisLeftKline);
        frameKline.setGridRows(6);
        frameKline.setGridColumns(6);
        Frame frameRSI = new Frame();
        frameRSI.setLeftAxis(yAxisLeftRSI);
        frameRSI.setGridRows(6);
        frameRSI.setGridColumns(6);
        ////////////////////// DRAWING AREA //////////////////////


        ////////////////////// CHART VIEW FRAGMENT //////////////////////
        final Chart chartKline = new Chart();
        chartKline.setFrame(frameKline);
        chartKline.getFrame().setViewport(new Viewport());
        chartKline.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        chartKline.setAutoScale(true);
        chartKline.setGridRows(6);
        chartKline.setGridColumns(6);


        final Chart chartRSI = new Chart();
        chartRSI.setFrame(frameRSI);
        chartRSI.getFrame().setViewport(new Viewport());
        chartRSI.setGridLineColor(ContextCompat.getColor(this, R.color.chart_rsi3));
        chartRSI.setAutoScale(false);
        chartRSI.setGridRows(6);
        chartRSI.setGridColumns(6);

        final KLineElement componentKlineChart = new KLineElement(chartKline);
        componentKlineChart.setDecreasingColor(ContextCompat.getColor(this, R.color.chart_red));
        componentKlineChart.setIncreasingColor(ContextCompat.getColor(this, R.color.chart_green));
        componentKlineChart.setKlineWidth(getDimension(R.dimen.chart_kline_width));
        componentKlineChart.setKlineInnerLineWidth(getDimension(R.dimen.chart_kline_inner_width));
        chartKline.addDrawingElement(componentKlineChart);

        final RSIElement drawingRSI5Chart = new RSIElement(chartRSI);
        drawingRSI5Chart.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi1));
        drawingRSI5Chart.setLineWidth(getDimension(R.dimen.chart_rsi_line_width));

        final RSIElement drawingRSI9Chart = new RSIElement(chartRSI);
        drawingRSI9Chart.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi2));
        drawingRSI9Chart.setLineWidth(getDimension(R.dimen.chart_rsi_line_width));

        final RSIElement drawingRSI14Chart = new RSIElement(chartRSI);
        drawingRSI14Chart.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi3));
        drawingRSI14Chart.setLineWidth(getDimension(R.dimen.chart_rsi_line_width));

        chartRSI.addDrawingElement(drawingRSI5Chart);
        chartRSI.addDrawingElement(drawingRSI9Chart);
        chartRSI.addDrawingElement(drawingRSI14Chart);
        ////////////////////// END CHART VIEW FRAGMENT //////////////////////

        stockChartView.addChartViewFragment(chartKline);
        stockChartView.addChartViewFragment(chartRSI);


        stockChartView.setTimeUnit(TimeUnit.ONE_DAY);
        stockChartView.setMaxIndex(klines.size() - 1);
        stockChartView.setLastDate(klines.get(klines.size() - 1).openTime);
        stockChartView.setScreenDataCount(60);

        stockChartView.setBackgroundColor(ContextCompat.getColor(this, R.color.chart_background));
        stockChartView.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        stockChartView.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        stockChartView.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        stockChartView.setViewSeparatorColor(ContextCompat.getColor(this, R.color.chart_view_separator));
        stockChartView.setTextYearSize((int) getDimension(R.dimen.chart_text_year_size));
        stockChartView.setTextYearColor(ContextCompat.getColor(this, R.color.chart_text_year));

        stockChartView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                stockChartView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Viewport viewportChartView = stockChartView.getViewportChartView();
                Viewport viewport = new Viewport();
                viewport.setViewWidth(viewportChartView.getViewWidth());
                viewport.setViewHeight(viewportChartView.getViewHeight());
                viewport.setViewingPosition(new Rect(0, 0, viewportChartView.getViewWidth(), viewportChartView.getViewHeight() / 2));

                Viewport viewportRSI = new Viewport();
                viewportRSI.setViewWidth(viewportChartView.getViewWidth());
                viewportRSI.setViewHeight(viewportChartView.getViewHeight());
                viewportRSI.setViewingPosition(new Rect(0, viewportChartView.getViewHeight() / 2, viewportChartView.getViewWidth(), viewportChartView.getViewHeight()));

                chartKline.getFrame().setViewport(viewport);
                chartRSI.getFrame().setViewport(viewportRSI);

                stockChartView.invalidate();
                Log.d("KLineActivity2" , "onGlobalLayout --- chartView.getWidth " + stockChartView.getWidth());
                Log.d("KLineActivity2" , "onGlobalLayout --- chartView.getHeight " + stockChartView.getHeight());

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

                componentKlineChart.setChartData(klinesSet);
                drawingRSI5Chart.setChartData(rsiSet1);
                drawingRSI9Chart.setChartData(rsiSet2);
                drawingRSI14Chart.setChartData(rsiSet3);

                stockChartView.invalidate();

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

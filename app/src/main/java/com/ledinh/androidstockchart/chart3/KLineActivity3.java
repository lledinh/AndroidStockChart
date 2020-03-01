package com.ledinh.androidstockchart.chart3;

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
import com.ledinh.androidstockchart.R;
import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.model.Kline;
import com.ledinh.androidstockchart.chart.set.KlinesSet;
import com.ledinh.androidstockchart.chart.set.RSISet;
import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart3.drawing.ChartDrawer;
import com.ledinh.androidstockchart.chart3.drawing.expanded.ExpandedGridDrawing;
import com.ledinh.androidstockchart.chart3.drawing.simple.StaticGridDrawing;
import com.ledinh.androidstockchart.chart3.drawing.KLineDrawing;
import com.ledinh.androidstockchart.chart3.drawing.RSIDrawing;
import com.ledinh.androidstockchart.chart3.drawing.StockChartDrawer;
import com.ledinh.androidstockchart.chart3.drawing.YAxisDrawing;
import com.ledinh.androidstockchart.chart3.element.Chart;
import com.ledinh.androidstockchart.chart3.element.simple.StaticGrid;
import com.ledinh.androidstockchart.chart3.element.KLineElement;
import com.ledinh.androidstockchart.chart3.element.RSIElement;
import com.ledinh.androidstockchart.chart3.element.StockChart;
import com.ledinh.androidstockchart.math.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class KLineActivity3 extends AppCompatActivity {

    StockChartView3 stockChartView;

    private float getDimension(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline3);

        stockChartView = findViewById(R.id.chart);

        final StockChart stockChart = new StockChart();

        ///////////// KLINE /////////////
        StaticGrid staticGridKLine = new StaticGrid(5, stockChart.getColumns());

        final YAxis yAxisKLine = new YAxis();
        yAxisKLine.setAxisMax(100);
        yAxisKLine.setAxisMin(0);

        Chart chartKLine = new Chart();
        chartKLine.setStaticGrid(staticGridKLine);
        chartKLine.setyAxisLeft(yAxisKLine);
        chartKLine.setAutoScale(true);

        final KLineElement kLineElement = new KLineElement();
        chartKLine.addChartElement(kLineElement);
        ////////////////////////////////


        ///////////// RSI /////////////
        StaticGrid staticGridRSI = new StaticGrid(5, stockChart.getColumns());

        final YAxis yAxisRSI = new YAxis();
        yAxisKLine.setAxisMax(100);
        yAxisKLine.setAxisMin(0);

        Chart chartRSI = new Chart();
        chartRSI.setStaticGrid(staticGridRSI);
        chartRSI.setyAxisLeft(yAxisRSI);
        chartRSI.setAutoScale(false);

        final RSIElement rsiElement5 = new RSIElement();
        final RSIElement rsiElement9 = new RSIElement();
        final RSIElement rsiElement14 = new RSIElement();
        chartRSI.addChartElement(rsiElement5);
        chartRSI.addChartElement(rsiElement9);
        chartRSI.addChartElement(rsiElement14);
        ////////////////////////////////

        stockChart.addChart(chartKLine);
        stockChart.addChart(chartRSI);

        final StockChartDrawer stockChartDrawer = new StockChartDrawer();
        stockChartDrawer.setScreenDataCount(60);

        //////////////////
        ChartDrawer chartDrawerKLine = new ChartDrawer();
        chartDrawerKLine.setWeight(4);

        StaticGridDrawing staticGridDrawingKLine = new StaticGridDrawing();
        staticGridDrawingKLine.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        staticGridDrawingKLine.setGridLineSize(getDimension(R.dimen.chart_grid_line_width));

        ExpandedGridDrawing expandedGridDrawing = new ExpandedGridDrawing();
        staticGridDrawingKLine.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        staticGridDrawingKLine.setGridLineSize(getDimension(R.dimen.chart_grid_line_width));



        YAxisDrawing yAxisLeftKLineDrawing = new YAxisDrawing();
        yAxisLeftKLineDrawing.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        yAxisLeftKLineDrawing.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftKLineDrawing.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
        yAxisLeftKLineDrawing.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftKLineDrawing.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
        yAxisLeftKLineDrawing.setLeftPadding((int) getDimension(R.dimen.chart_axis_padding));

        chartDrawerKLine.setStaticGridDrawing(staticGridDrawingKLine);
        chartDrawerKLine.setyAxisLeftDrawing(yAxisLeftKLineDrawing);

        final KLineDrawing kLineDrawing = new KLineDrawing();
        kLineDrawing.getPaintDecreasing().setColor(ContextCompat.getColor(KLineActivity3.this, R.color.chart_red));
        kLineDrawing.getPaintIncreasing().setColor(ContextCompat.getColor(KLineActivity3.this, R.color.chart_green));
        kLineDrawing.setKlineWidth(getDimension(R.dimen.chart_kline_width));
        kLineDrawing.setKlineInnerLineWidth(getDimension(R.dimen.chart_kline_inner_width));

        chartDrawerKLine.addChartElementDrawer(kLineDrawing);

        ///////////////
        ChartDrawer chartDrawerRSI = new ChartDrawer();
        chartDrawerRSI.setWeight(1);

        StaticGridDrawing staticGridDrawingRSI = new StaticGridDrawing();
        staticGridDrawingRSI.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        staticGridDrawingRSI.setGridLineSize(getDimension(R.dimen.chart_grid_line_width));

        YAxisDrawing yAxisLeftRSIDrawing = new YAxisDrawing();
        yAxisLeftRSIDrawing.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        yAxisLeftRSIDrawing.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftRSIDrawing.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
        yAxisLeftRSIDrawing.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftRSIDrawing.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
        yAxisLeftRSIDrawing.setLeftPadding((int) getDimension(R.dimen.chart_axis_padding));

        chartDrawerRSI.setStaticGridDrawing(staticGridDrawingRSI);
        chartDrawerRSI.setyAxisLeftDrawing(yAxisLeftRSIDrawing);

        final RSIDrawing rsiDrawing5 = new RSIDrawing();
        rsiDrawing5.setLineColor(ContextCompat.getColor(KLineActivity3.this, R.color.chart_rsi1));
        rsiDrawing5.setLineWidth(getDimension(R.dimen.chart_rsi_line_width));
        final RSIDrawing rsiDrawing9 = new RSIDrawing();
        rsiDrawing9.setLineColor(ContextCompat.getColor(KLineActivity3.this, R.color.chart_rsi2));
        rsiDrawing9.setLineWidth(getDimension(R.dimen.chart_rsi_line_width));
        final RSIDrawing rsiDrawing14 = new RSIDrawing();
        rsiDrawing14.setLineColor(ContextCompat.getColor(KLineActivity3.this, R.color.chart_rsi3));
        rsiDrawing14.setLineWidth(getDimension(R.dimen.chart_rsi_line_width));

        chartDrawerRSI.addChartElementDrawer(rsiDrawing5);
        chartDrawerRSI.addChartElementDrawer(rsiDrawing9);
        chartDrawerRSI.addChartElementDrawer(rsiDrawing14);

        ///////////////

        stockChartDrawer.addChartDrawer(chartKLine, chartDrawerKLine);
        stockChartDrawer.addChartDrawer(chartRSI, chartDrawerRSI);

        stockChartDrawer.getStaticTimelineDrawer().setTextColor(ContextCompat.getColor(this, R.color.chart_text));
        stockChartDrawer.getStaticTimelineDrawer().setTextSize((int) getDimension(R.dimen.chart_text_size));
        stockChartDrawer.getStaticTimelineDrawer().setTextYearColor(ContextCompat.getColor(this, R.color.chart_text));
        stockChartDrawer.getStaticTimelineDrawer().setTextYearSize((int) getDimension(R.dimen.chart_text_size));

        stockChartView.setStockChart(stockChart);
        stockChartView.setStockChartDrawer(stockChartDrawer);

        final List<Kline> klines = readFileSample();

        stockChartView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        KlinesSet klinesSet = new KlinesSet(klines, TimeUnit.ONE_DAY);
                        kLineElement.setData(klinesSet);
                        Pair<Integer, Integer> range = klinesSet.getRange();
                        float min = range.first;
                        float max = range.second;
                        yAxisKLine.setRange(min, max);

                        stockChart.getStaticTimeline().setInitialDate(klines.get(0).openTime);
                        stockChart.getStaticTimeline().setTimeUnit(TimeUnit.ONE_DAY);

                        RSISet rsiSet1 = Calculator.rsi(klinesSet, 5);
                        RSISet rsiSet2 = Calculator.rsi(klinesSet, 9);
                        RSISet rsiSet3 = Calculator.rsi(klinesSet, 14);
                        rsiElement5.setData(rsiSet1);
                        rsiElement9.setData(rsiSet2);
                        rsiElement14.setData(rsiSet3);
                        yAxisRSI.setRange(0, 100);

                        stockChartView.invalidate();

                        Log.d("KLineActivity", "klines size = " + klines.size());
                    }
                });

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
        gsonBuilder.registerTypeAdapter(Kline.class, new KLineActivity3.KlineDeserializer());
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

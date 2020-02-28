package com.ledinh.androidstockchart.chart3;

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
import com.ledinh.androidstockchart.KLineActivity2;
import com.ledinh.androidstockchart.R;
import com.ledinh.androidstockchart.chart.YAxis;
import com.ledinh.androidstockchart.chart.model.Kline;
import com.ledinh.androidstockchart.chart.set.KlinesSet;
import com.ledinh.androidstockchart.chart.set.RSISet;
import com.ledinh.androidstockchart.chart.util.TimeUnit;
import com.ledinh.androidstockchart.chart.util.Viewport;
import com.ledinh.androidstockchart.chart.view.element.RSIElement;
import com.ledinh.androidstockchart.chart3.drawing.ChartDrawer;
import com.ledinh.androidstockchart.chart3.drawing.ChartElementDrawer;
import com.ledinh.androidstockchart.chart3.drawing.GridDrawing;
import com.ledinh.androidstockchart.chart3.drawing.KLineDrawing;
import com.ledinh.androidstockchart.chart3.drawing.StockChartDrawer;
import com.ledinh.androidstockchart.chart3.drawing.YAxisDrawing;
import com.ledinh.androidstockchart.chart3.element.Chart;
import com.ledinh.androidstockchart.chart3.element.ChartElement;
import com.ledinh.androidstockchart.chart3.element.Grid;
import com.ledinh.androidstockchart.chart3.element.KLineElement;
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

        StockChart stockChart = new StockChart();

        Grid grid = new Grid(5, 5);

        final YAxis yAxis = new YAxis();
        yAxis.setAxisMax(100);
        yAxis.setAxisMin(0);

        Chart chart = new Chart();
        chart.setGrid(grid);
        chart.setyAxisLeft(yAxis);

        final KLineElement kLineElement = new KLineElement();
        chart.addChartElement(kLineElement);

        stockChart.addChart(chart);


        StockChartDrawer stockChartDrawer = new StockChartDrawer();
        stockChartDrawer.setScreenDataCount(60);

        ChartDrawer chartDrawer = new ChartDrawer();

        GridDrawing gridDrawing = new GridDrawing();
        gridDrawing.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
        gridDrawing.setGridLineSize(getDimension(R.dimen.chart_grid_line_width));

        YAxisDrawing yAxisLeftDrawing = new YAxisDrawing();
        yAxisLeftDrawing.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
        yAxisLeftDrawing.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftDrawing.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
        yAxisLeftDrawing.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
        yAxisLeftDrawing.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
        yAxisLeftDrawing.setLeftPadding((int) getDimension(R.dimen.chart_axis_padding));

        chartDrawer.setGridDrawing(gridDrawing);
        chartDrawer.setyAxisLeftDrawing(yAxisLeftDrawing);

        final KLineDrawing kLineDrawing = new KLineDrawing();
        kLineDrawing.getPaintDecreasing().setColor(ContextCompat.getColor(KLineActivity3.this, R.color.chart_red));
        kLineDrawing.getPaintIncreasing().setColor(ContextCompat.getColor(KLineActivity3.this, R.color.chart_green));
        kLineDrawing.setKlineWidth(getDimension(R.dimen.chart_kline_width));
        kLineDrawing.setKlineInnerLineWidth(getDimension(R.dimen.chart_kline_inner_width));
        kLineDrawing.setSpaceBetweenValue(10);


        chartDrawer.addChartElementDrawer(kLineDrawing);

        stockChartDrawer.addChartDrawer(chart, chartDrawer);


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
                        yAxis.setRange(min, max);

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

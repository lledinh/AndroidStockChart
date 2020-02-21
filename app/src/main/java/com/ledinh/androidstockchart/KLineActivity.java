//package com.ledinh.androidstockchart;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.DimenRes;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParseException;
//import com.google.gson.reflect.TypeToken;
//import com.ledinh.androidstockchart.chart.YAxis;
//import com.ledinh.androidstockchart.chart.model.Kline;
//import com.ledinh.androidstockchart.chart.view.element.DrawingArea;
//import com.ledinh.androidstockchart.chart.view.element.DrawingRSIChart;
//import com.ledinh.androidstockchart.chart.set.KlinesSet;
//import com.ledinh.androidstockchart.chart.set.RSISet;
//import com.ledinh.androidstockchart.chart.view.element.DrawingKlineChart;
//import com.ledinh.androidstockchart.chart.util.TimeUnit;
//import com.ledinh.androidstockchart.chart.view.ChartView;
//import com.ledinh.androidstockchart.math.Calculator;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.lang.reflect.Type;
//import java.util.List;
//
//public class KLineActivity extends AppCompatActivity {
//
////    KLineChartView kLineChartView;
////    RSIChartView rsiChartView;
//    ChartView chartView;
//
//    private float getDimension(@DimenRes int resId) {
//        return getResources().getDimension(resId);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_kline);
//
////        kLineChartView = findViewById(R.id.kchart_view);
////        rsiChartView = findViewById(R.id.rsichart_view);
//
//        chartView = findViewById(R.id.chart2);
//
//        final List<Kline> klines = readFileSample();
//
//        chartView.setTimeUnit(TimeUnit.ONE_DAY);
//        chartView.setMaxIndex(klines.size() - 1);
//        chartView.setLastDate(klines.get(klines.size() - 1).openTime);
//        chartView.setScreenDataCount(60);
//
//        chartView.setBackgroundColor(ContextCompat.getColor(this, R.color.chart_background));
//        chartView.setGridLineColor(ContextCompat.getColor(this, R.color.chart_grid_line));
//        chartView.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
//        chartView.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
//        chartView.setViewSeparatorColor(ContextCompat.getColor(this, R.color.chart_view_separator));
//        chartView.setTextYearSize((int) getDimension(R.dimen.chart_text_year_size));
//        chartView.setTextYearColor(ContextCompat.getColor(this, R.color.chart_text_year));
//
//        YAxis yAxisLeftKline = new YAxis();
//        yAxisLeftKline.setPosition(YAxis.Position.LEFT);
//        yAxisLeftKline.setGridRows(4);
//        yAxisLeftKline.setUnit("Dollar");
//        yAxisLeftKline.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
//        yAxisLeftKline.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
//        yAxisLeftKline.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
//        yAxisLeftKline.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
//        yAxisLeftKline.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
//
//        YAxis yAxisLeftRSI = new YAxis();
//        yAxisLeftRSI.setPosition(YAxis.Position.LEFT);
//        yAxisLeftRSI.setGridRows(4);
//        yAxisLeftRSI.setUnit("%");
//        yAxisLeftRSI.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
//        yAxisLeftRSI.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
//        yAxisLeftRSI.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
//        yAxisLeftRSI.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
//        yAxisLeftRSI.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
//
//
////        final DrawingKlineChart klineDrawing = new DrawingKlineChart(chartView);
////        final DrawingKlineChart klineDrawing = new DrawingKlineChart();
////        klineDrawing.setDecreasingColor(ContextCompat.getColor(this, R.color.chart_red));
////        klineDrawing.setIncreasingColor(ContextCompat.getColor(this, R.color.chart_green));
////        klineDrawing.setKlineWidth(getDimension(R.dimen.chart_kline_width));
////        klineDrawing.setKlineInnerLineWidth(getDimension(R.dimen.chart_kline_inner_width));
////        klineDrawing.setSelectedKlineColor(ContextCompat.getColor(this, R.color.chart_yellow));
////        klineDrawing.setSelectedKlineLineWidth(getDimension(R.dimen.chart_selector_line_width));
////        klineDrawing.setAutoScale(true);
////        klineDrawing.setWeight(2);
////        klineDrawing.setYAxis(yAxisLeftKline);
////        klineDrawing.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
////        klineDrawing.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
////        klineDrawing.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
////        klineDrawing.setTextAxisLeftPadding((int) getDimension(R.dimen.chart_axis_padding));
////        klineDrawing.setUnit("Dollar");
////        klineDrawing.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
////        klineDrawing.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
//
//        DrawingArea drawingAreaKline;
//        DrawingArea drawingAreaRSI;
//
//
//        YAxis yAxisRightKline = new YAxis();
//        yAxisRightKline.setPosition(YAxis.Position.RIGHT);
//
//        drawingAreaKline = new DrawingArea(yAxisLeftKline, yAxisRightKline);
//        drawingAreaRSI = new DrawingArea();
//
//        klineDrawing.setDrawingArea(drawingAreaKline);
//
//
////        final DrawingRSIChart drawingRSI = new DrawingRSIChart(chartView);
//        final DrawingRSIChart drawingRSI = new DrawingRSIChart();
//        drawingRSI.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi1));
//        drawingRSI.setLineWidth(getDimension(R.dimen.chart_kline_inner_width));
//        drawingRSI.setAutoScale(true);
//        drawingRSI.setWeight(1);
//        drawingRSI.setyAxis(yAxisLeftRSI);
////        drawingRSI.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
////        drawingRSI.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
////        drawingRSI.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
////        drawingRSI.setAxisMin(0);
////        drawingRSI.setAxisMax(100);
////        drawingRSI.setTextAxisLeftPadding((int) getDimension(R.dimen.chart_axis_padding));
////        drawingRSI.setUnit("%");
////        drawingRSI.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
////        drawingRSI.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
//
//
//
////        final DrawingRSIChart drawingRSI2 = new DrawingRSIChart(chartView);
//        final DrawingRSIChart drawingRSI2 = new DrawingRSIChart();
//        drawingRSI2.setLineColor(ContextCompat.getColor(this, R.color.chart_rsi2));
//        drawingRSI2.setLineWidth(getDimension(R.dimen.chart_kline_inner_width));
//        drawingRSI2.setAutoScale(false);
//        drawingRSI2.setAxisMin(0);
//        drawingRSI2.setAxisMax(100);
//        drawingRSI2.setWeight(1);
//        drawingRSI2.setTextAxisSize((int) getDimension(R.dimen.chart_text_size));
//        drawingRSI2.setTextAxisColor(ContextCompat.getColor(this, R.color.chart_text));
//        drawingRSI2.setTextAxisBackgroundColor(ContextCompat.getColor(this, R.color.chart_background2));
//        drawingRSI2.setTextAxisLeftPadding((int) getDimension(R.dimen.chart_axis_padding));
//        drawingRSI2.setUnit("%");
//        drawingRSI2.setTextUnitColor(ContextCompat.getColor(this, R.color.chart_text));
//        drawingRSI2.setTextUnitSize((int) getDimension(R.dimen.chart_text_unit_size));
//
//        chartView.addDrawingElement(klineDrawing);
//        chartView.addDrawingElement(drawingRSI);
//        chartView.addDrawingElement(drawingRSI2);
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                KlinesSet klinesSet = new KlinesSet(klines, TimeUnit.ONE_DAY);
//                RSISet rsiSet1 = Calculator.rsi(klinesSet, 5);
//                RSISet rsiSet2 = Calculator.rsi(klinesSet, 14);
//
//                klineDrawing.setChartData(klinesSet);
//                chartView.invalidate();
//
//                drawingRSI.setChartData(rsiSet1);
//                chartView.invalidate();
//
//                drawingRSI2.setChartData(rsiSet2);
//                chartView.invalidate();
//
//                Log.d("KLineActivity", "klines size = " + klines.size());
//            }
//        });
//    }
//
//    public List<Kline> readFileSample() {
//        StringBuilder s = new StringBuilder();
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(
//                    new InputStreamReader(getAssets().open("sample_binance")));
//
//            String mLine;
//            while ((mLine = reader.readLine()) != null) {
//                s.append(mLine);
//            }
//        } catch (IOException e) {
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//
//                }
//            }
//        }
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Kline.class, new KlineDeserializer());
//        Gson gson = gsonBuilder.create();
//
//        final List<Kline> data = gson.fromJson(s.toString(), new TypeToken<List<Kline>>() {}.getType());
//
//        return data;
//    }
//
//    class KlineDeserializer implements JsonDeserializer<Kline>
//    {
//
//        @Override
//        public Kline deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            JsonArray jsonArray = json.getAsJsonArray();
//
//            long openTime = jsonArray.get(0).getAsLong();
//            double open = jsonArray.get(1).getAsDouble();
//            double high = jsonArray.get(2).getAsDouble();
//            double low = jsonArray.get(3).getAsDouble();
//            double close = jsonArray.get(4).getAsDouble();
//            double volume = jsonArray.get(5).getAsDouble();
//            long closeTime = jsonArray.get(6).getAsLong();
//            double quoteAssetVolume = jsonArray.get(7).getAsDouble();
//            long numberOfTrades = jsonArray.get(8).getAsLong();
//            double takerBuyBaseAssetVolume = jsonArray.get(9).getAsDouble();
//            double takerBuyQuoteAssetVolume = jsonArray.get(10).getAsDouble();
//            double ignore = jsonArray.get(11).getAsDouble();
//
//            return new Kline(openTime, open, high, low, close, volume, closeTime);
//        }
//    }
//}

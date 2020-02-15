package com.ledinh.androidstockchart;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

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
import com.ledinh.androidstockchart.chart.KlinesSet;
import com.ledinh.androidstockchart.chart.RSIChartView;
import com.ledinh.androidstockchart.chart.RSISet;
import com.ledinh.androidstockchart.chart.TimeUnit;
import com.ledinh.androidstockchart.math.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class KLineActivity extends AppCompatActivity {

    KLineChartView kLineChartView;
    RSIChartView rsiChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline);

        kLineChartView = findViewById(R.id.kchart_view);
        rsiChartView = findViewById(R.id.rsichart_view);

        final List<Kline> klines = readFileSample();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("KLineActivity", "OK");
                Log.d("KLineActivity", "klines size = " + klines.size());

                KlinesSet klinesSet = new KlinesSet(klines, TimeUnit.ONE_DAY);
                kLineChartView.setKlinesSet(klinesSet);
                kLineChartView.invalidate();

                RSISet rsiSet1 = Calculator.rsi(klinesSet, 5);
                RSISet rsiSet2 = Calculator.rsi(klinesSet, 9);
                RSISet rsiSet3 = Calculator.rsi(klinesSet, 14);

                rsiChartView.addRsiSet(rsiSet1);
                rsiChartView.addRsiSet(rsiSet2);
                rsiChartView.addRsiSet(rsiSet3);

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

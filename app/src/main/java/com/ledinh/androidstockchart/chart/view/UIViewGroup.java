package com.ledinh.androidstockchart.chart.view;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart.YAxis;

import java.util.ArrayList;
import java.util.List;

public abstract class UIViewGroup extends UIElement {
    private List<UIElement> uiElements;

    public UIViewGroup() {
        uiElements = new ArrayList<>();
    }

    public UIViewGroup(Rect position) {
        super(position);
        uiElements = new ArrayList<>();
    }

    public UIViewGroup(List<UIElement> uiElements) {
        this.uiElements = uiElements;
    }

    public UIViewGroup(Rect position, List<UIElement> uiElements) {
        super(position);
        this.uiElements = uiElements;
    }

    @Override
    public void draw(Canvas canvas, float beginX, float translateX) {
        for (UIElement uiElement : uiElements) {
            uiElement.draw(canvas, beginX, translateX);
        }
    }
}

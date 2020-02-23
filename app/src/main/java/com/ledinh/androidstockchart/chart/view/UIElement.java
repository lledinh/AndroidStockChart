package com.ledinh.androidstockchart.chart.view;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.ledinh.androidstockchart.chart.YAxis;

public abstract class UIElement {
    protected Rect position;

    public UIElement() {
        position = new Rect();
    }

    public UIElement(Rect position) {
        this.position = position;
    }

    public abstract void draw(Canvas canvas, YAxis yAxis, float beginDrawX, float translateX);

    public void draw(Canvas canvas, YAxis yAxis, float beginDrawX) {
        draw(canvas, yAxis, beginDrawX, 0);
    }

    public int getWidth() {
        return position.right - position.left;
    }

    public int getHeight() {
        return position.bottom - position.top;
    }

    public int getLeft() {
        return position.left;
    }

    public int getTop() {
        return position.top;
    }

    public int getRight() {
        return position.right;
    }

    public int getBottom() {
        return position.bottom;
    }

    public void setLeft(int left) {
        position.left = left;
    }

    public void setTop(int top) {
        position.top = top;
    }

    public void setRight(int right) {
        position.right = right;
    }

    public void setBottom(int bottom) {
        position.bottom = bottom;
    }

}

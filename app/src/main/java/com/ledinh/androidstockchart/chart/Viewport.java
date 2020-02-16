package com.ledinh.androidstockchart.chart;

import android.graphics.Rect;

public class Viewport {
    private int viewWidth;
    private int viewHeight;
    private float minPosX;
    private float maxPosX;
    private float minPosY;
    private float maxPosY;
    private float translateX;
    private float translateY;
    private Rect viewingPosition;

    public Viewport() {
        viewWidth = 0;
        viewHeight = 0;
        minPosX = Float.MIN_VALUE;
        maxPosX = Float.MAX_VALUE;
        minPosY = Float.MIN_VALUE;
        minPosY = Float.MAX_VALUE;
        translateX = 0;
        translateY = 0;

        viewingPosition = new Rect(0,0,0,0);
    }

    public Viewport(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        minPosX = Float.MIN_VALUE;
        maxPosX = Float.MAX_VALUE;
        minPosY = Float.MIN_VALUE;
        minPosY = Float.MAX_VALUE;
        translateX = 0;
        translateY = 0;

        viewingPosition = new Rect(0,0,viewWidth,viewHeight);
    }

    public float getViewportHeight() {
        return viewingPosition.bottom - viewingPosition.top;
    }

    public float getViewportWidth() {
        return viewingPosition.right - viewingPosition.left;
    }

    public void translate(float x, float y) {
        translateX = x;
        translateY = y;

        if (x > 0) {
            if (viewingPosition.left + x < maxPosX) {
                viewingPosition.right += x;
                viewingPosition.left += x;
            }
        }

        if (x < 0) {
            if (viewingPosition.right + x > minPosX) {
                viewingPosition.right += x;
                viewingPosition.left += x;
            }
        }
    }

    public float getMinPosX() {
        return minPosX;
    }

    public void setMinPosX(float minPosX) {
        this.minPosX = minPosX;
    }

    public float getMaxPosX() {
        return maxPosX;
    }

    public void setMaxPosX(float maxPosX) {
        this.maxPosX = maxPosX;
    }

    public float getMinPosY() {
        return minPosY;
    }

    public void setMinPosY(float minPosY) {
        this.minPosY = minPosY;
    }

    public float getMaxPosY() {
        return maxPosY;
    }

    public void setMaxPosY(float maxPosY) {
        this.maxPosY = maxPosY;
    }

    public float getTranslateX() {
        return translateX;
    }

    public void setTranslateX(float translateX) {
        this.translateX = translateX;
    }

    public float getTranslateY() {
        return translateY;
    }

    public void setTranslateY(float translateY) {
        this.translateY = translateY;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public Rect getViewingPosition() {
        return viewingPosition;
    }

    public void setViewingPosition(Rect viewingPosition) {
        this.viewingPosition = viewingPosition;
    }
}

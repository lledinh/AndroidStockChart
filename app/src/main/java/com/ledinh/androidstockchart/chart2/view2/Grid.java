package com.ledinh.androidstockchart.chart2.view2;

public class Grid {
    private int gridRows;
    private int gridColumns;

    public Grid() {
        gridRows = 2;
        gridColumns = 2;
    }

    public Grid(int gridRows, int gridColumns) {
        this.gridRows = gridRows;
        this.gridColumns = gridColumns;
    }

    public int getGridRows() {
        return gridRows;
    }

    public void setGridRows(int gridRows) {
        this.gridRows = gridRows;
    }

    public int getGridColumns() {
        return gridColumns;
    }

    public void setGridColumns(int gridColumns) {
        this.gridColumns = gridColumns;
    }
}

package com.ledinh.androidstockchart.chart3.element.expanded;

public class ExpandedGrid {
    private int gridRows;
    private int gridColumns;

    public ExpandedGrid() {
        gridRows = 2;
        gridColumns = 2;
    }

    public ExpandedGrid(int gridRows, int gridColumns) {
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

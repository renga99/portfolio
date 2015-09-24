package com.lee.ysl.selfi;



public class Card {
    private String line1;
    private String line2;

    public Card(String line1, String line2) {
        this.line1 = line1;
        this.line2 = line2;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    @Override
    public String toString() {
        return line2;
    }
}

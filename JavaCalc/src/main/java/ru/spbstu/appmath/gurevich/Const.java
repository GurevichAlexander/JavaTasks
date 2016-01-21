package ru.spbstu.appmath.gurevich;

/*
 * This class implements expressions of constants
 */

public class Const implements Expression {
    private double value = 0;

    public Const(final double val) {
        this.value = val;
    }

    public double calc(final double x) {
        return this.value;
    }

}
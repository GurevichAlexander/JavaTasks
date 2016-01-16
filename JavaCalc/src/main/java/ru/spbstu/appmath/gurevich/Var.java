package ru.spbstu.appmath.gurevich;

/*
 * This class implements expressions of variables
 */

public class Var implements Expression {
    public double calc(double x) {
        return x;
    }
}
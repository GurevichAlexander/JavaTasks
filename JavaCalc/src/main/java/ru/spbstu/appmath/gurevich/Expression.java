package ru.spbstu.appmath.gurevich;
/*
 * This interface implements exception-throwing classes of expressions
 */

public interface Expression {
    double calc(double x) throws Exception;
}

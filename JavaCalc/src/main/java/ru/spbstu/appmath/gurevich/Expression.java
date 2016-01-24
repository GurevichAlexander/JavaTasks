package ru.spbstu.appmath.gurevich;
import ru.spbstu.appmath.gurevich.exceptions.singlecalcexceptions.CalculationException;
/*
 * This interface implements exception-throwing classes of expressions
 */

public interface Expression {
    double calc(double x) throws CalculationException;
}

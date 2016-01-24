package ru.spbstu.appmath.gurevich;
import ru.spbstu.appmath.gurevich.exceptions.singlecalcexceptions.CalculationException;
import ru.spbstu.appmath.gurevich.exceptions.singlecalcexceptions.SingleCalcException;
/*
 * Class Binary implements calculation
 * of any expression looking like [EXPRESSION OPERATOR EXPRESSION] whether it contains
 * variables or constants
 */

public class Binary implements Expression{
    private Expression left = null;
    private Expression right = null;
    private char operation = 0;

    public Binary(Expression left, Expression right, char operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public double calc(double x) throws CalculationException{
        double resLeft = this.left.calc(x);
        double resRight = this.right.calc(x);
        double result;
        switch (this.operation) {
            case '+':
                result = resLeft + resRight;
                break;
            case '-':
                result = resLeft - resRight;
                break;
            case '*':
                result = resLeft * resRight;
                break;
            case '/':
                if (resRight == 0)
                    throw new CalculationException("Division by zero");
                result = resLeft / resRight;
                break;
            default:
                result = 0;
        }
        return result;
    }
}
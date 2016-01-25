package ru.spbstu.appmath.gurevich;

import ru.spbstu.appmath.gurevich.exceptions.singlecalcexceptions.SingleCalcException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

@RunWith(Parameterized.class)
public class CalcTest {
    private static final Double epsilon = Math.pow(10, -15);
    private String expression;
    private Double variable;
    private Double result;
    private String exception;


    public CalcTest(String expression, Double variable, Double result, String exceptionMessage) {
        this.expression = expression;
        this.variable = variable;
        this.result = result;
        this.exception = exceptionMessage;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData(){
        try (Scanner f = new Scanner(CalcTest.class.getClassLoader().getResourceAsStream("tests.txt"))) {
            ArrayList<Object[]> tests = new ArrayList<Object[]>();
            while (f.hasNextLine()) {
                String line = f.nextLine();
                String[] data = line.split(";");
                tests.add(new Object[]{data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]), data[3]});
            }
            f.close();
            return tests;
          //  System.out.println("\"tests.txt\" is not in resources directory");
        }
    }

    @Test
    public void test() {
        try {
            final Expression f;
            final Parser p = new Parser();
            f = p.parse(expression);
            double counted = f.calc(variable);
            Assert.assertTrue("Wrong answer", Math.abs(counted - result) < epsilon);
        } catch (SingleCalcException e) {
            Assert.assertTrue("Wrong exception", exception.equals(e.getMessage()));
        }
    }

}
package ru.spbstu.appmath.gurevich;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;



public class Testfile {
    private Parser p = new Parser();

    private static String[] data = {
            "2+2",
            "2*3",
            "16/2",
            "10/3*   x        ",
            "x + (x -1)*x/(x - 2.5)",
            "((x+1)",
            "1/(5-x)"

    };

    private static Object[] answers = {
            4.0,
            6.0,
            8.0,
            10.0,
            12.0
    };

    @Test
    public void test() throws Exception{
        for (int i = 0; i < 5; i++) {
            Expression f = p.parse(data[i]);
            Assert.assertEquals(f.calc(i), answers[i]);
        }
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testException1() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Problem with brackets!");
        Expression f = p.parse(data[5]);
        double result = f.calc(5);
        System.out.println(result);
    }


    @Test
    public void testException2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Division by zero");
        Expression f = p.parse(data[6]);
        double result = f.calc(5);
        System.out.println(result);
    }


}

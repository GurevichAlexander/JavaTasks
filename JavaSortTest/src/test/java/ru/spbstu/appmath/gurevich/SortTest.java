package ru.spbstu.appmath.gurevich;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(Parameterized.class)
public class SortTest<T> {

    private Sort<T> sort;
    private T[] elements;
    private Comparator<T> comp;
    public SortTest (Sort<T> sort, T[] input, Comparator<T> comp) {
        this.sort = sort;
        this.elements = input;
        this.comp = comp;
    }

    public static final Selectionsort SELECTIONSORT = new Selectionsort();

    private static final Comparator<Double> DOUBLE_COMPARATOR_UP = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return o1.compareTo(o2);
        }
    };
    private static final Comparator<Double> DOUBLE_COMPARATOR_DOWN = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return o2.compareTo(o1);
        }
    };
    private static final Comparator<Bonds> COUPON_BONDS_COMPARATOR = new Comparator<Bonds>() {
        public int compare(final Bonds o1, final Bonds o2) {
            Double ob1 = o1.getPrice();
            Double ob2 = o2.getPrice();
            if (ob1.compareTo(ob2) != 0) {
                return ob1.compareTo(ob2);
            } else {
                String n1 = o1.getIssuer();
                String n2 = o2.getIssuer();
                return n1.compareTo(n2);
            }
        }
    };
    private static final Comparator<Bonds> ISSUER_BONDS_COMPARATOR = new Comparator<Bonds>() {
        public int compare(final Bonds o1, final Bonds o2) {
            String ob1 = o1.getIssuer();
            String ob2 = o2.getIssuer();
            if (ob1.compareTo(ob2) != 0) {
                return ob1.compareTo(ob2);
            } else {
                Double n1 = o1.getPrice();
                Double n2 = o2.getPrice();
                return n1.compareTo(n2);
            }
        }
    };


    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        Random random = new Random();
        Double[][] array= new Double[7][10];
        Double tmp1 = random.nextDouble() * 10;
        Double tmp2 = random.nextDouble() * -10;
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < array[0].length; j++)
            {
                if (i < 3) {
                    array[i][j] = 2000 * random.nextDouble() - 1000;
                }
                if (i == 3) {
                    array[i][j] = tmp1;
                }
                if (i == 4) {
                    if (j % 2 == 0) {
                        array[i][j] = tmp1;
                    } else {
                        array[i][j] = tmp2;
                    }
                }
            }
        }
        array[5] = new Double[]{10.01, 10.05, 32.3, -2.1, 23.32, 4.3, 8.56, 8.5, 10.10};
        array[6] = new Double[]{8.92, 6.21, 2.14, 1.23, 0.01, -2.35, -4.25, -8.88, -9.32, -15.75};

        Bonds[][] portfolio = new Bonds[][] {
                new Bonds[]{new Bonds("MSFT", 20.04), new Bonds("DELL", 32.21),
                        new Bonds("AAPL", 123.2), new Bonds("AAPL", 151.21)},
                new Bonds[]{new Bonds("BP", 321.2), new Bonds("BARC", 12.2),
                        new Bonds("URKA", 164.9), new Bonds("DELL", 9.123)},
                new Bonds[]{new Bonds("MSFT", 35.5), new Bonds("URKA", 32.31),
                        new Bonds("DBK", 64.6), new Bonds("BP", 987.98)},
                new Bonds[]{new Bonds("AAPL", 35.093), new Bonds("GAZP", 35.23),
                        new Bonds("INTC", 350.92), new Bonds("WBD", 900.02)},
                new Bonds[]{new Bonds("BARC", 24.64), new Bonds("DBK", 55.78),
                        new Bonds("IBM", 19.36), new Bonds("GAZP", 55.23)},
                new Bonds[]{new Bonds("WBD", 42.76), new Bonds("INTC", 12.12),
                        new Bonds("DBK", 80.008), new Bonds("DELL", 84.92)},
        };

        Object[][] data = {
                {SELECTIONSORT, array[0], DOUBLE_COMPARATOR_UP},
                {SELECTIONSORT, array[1], DOUBLE_COMPARATOR_UP},
                {SELECTIONSORT, array[2], DOUBLE_COMPARATOR_UP},
                {SELECTIONSORT, array[3], DOUBLE_COMPARATOR_UP},
                {SELECTIONSORT, array[4], DOUBLE_COMPARATOR_UP},
                {SELECTIONSORT, array[5], DOUBLE_COMPARATOR_UP},
                {SELECTIONSORT, array[6], DOUBLE_COMPARATOR_UP},
                {SELECTIONSORT, array[0], DOUBLE_COMPARATOR_DOWN},
                {SELECTIONSORT, array[1], DOUBLE_COMPARATOR_DOWN},
                {SELECTIONSORT, array[2], DOUBLE_COMPARATOR_DOWN},
                {SELECTIONSORT, array[3], DOUBLE_COMPARATOR_DOWN},
                {SELECTIONSORT, array[4], DOUBLE_COMPARATOR_DOWN},
                {SELECTIONSORT, array[5], DOUBLE_COMPARATOR_DOWN},
                {SELECTIONSORT, array[6], DOUBLE_COMPARATOR_DOWN},
                {SELECTIONSORT, portfolio[0],  COUPON_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[1],  COUPON_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[2],  COUPON_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[3],  COUPON_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[4],  COUPON_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[5],  COUPON_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[0],  ISSUER_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[1],  ISSUER_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[2],  ISSUER_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[3],  ISSUER_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[4],  ISSUER_BONDS_COMPARATOR},
                {SELECTIONSORT, portfolio[5],  ISSUER_BONDS_COMPARATOR},

        };
        return Arrays.asList(data);
    }


    @Test
    public void test() {
        T[] result = sort.sort(elements, comp);
        Assert.assertTrue(testOrder(result, comp));
        Assert.assertEquals("Result array length should be equal to original", elements.length, result.length);
        Assert.assertTrue(hasEachElementOf(elements, result, comp));
    }


    public static <T> boolean testOrder(T[] array, Comparator<T> comparator) {
        for (int i = 0; i < array.length - 1; i++) {
            if (comparator.compare(array[i], array[i + 1]) < 0)
                return false;
        }
        return true;
    }

    public static <T> boolean hasEachElementOf(T[] input, T[] result, Comparator<T> comparator) {
        int c1, c2;
        for (T element : input) {
            c1 = 0;
            c2 = 0;
            for (int j = 0; j < result.length; j++) {
                if (comparator.compare(result[j], element) == 0)
                    c1++;
                if (comparator.compare(input[j], element) == 0)
                    c2++;
            }
            if (c1 != c2)
                return false;
        }
        return true;
    }
}
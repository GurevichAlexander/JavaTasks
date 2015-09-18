package Javasort;


public class SelectionSort
{
    private static void printarray(double[] array)
    {
        for (int i = 0; i < array.length; ++i)
        {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


    public static void selectionsort(double[] num)
    {
        int i, j, first;
        double temp;
        for (i = num.length - 1; i > 0; i--)
        {
            first = 0;
            for (j = 1; j <= i; ++j)
            {
                if (num[j] < num[first])
                    first = j;
            }
            temp = num[first];
            num[first] = num[i];
            num[i] = temp;
        }
    }

    public static void main(String[] args)
    {
        double[] array;
        array = new double[args.length];
        for (int i = 0; i < args.length; ++i)
        {
            array[i] = Double.parseDouble(args[i]);
        }
        selectionsort(array);
        printarray(array);
    }

}

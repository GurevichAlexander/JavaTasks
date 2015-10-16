package javasort;


public class SelectionSort {
    private static void printArray(double[] array) {
        for (int i = 0; i < array.length; ++i) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


    public static void selectionSort(double[] num) {
        double temp;
        for (int i = num.length - 1; i > 0; i--) {
            int first = 0;
            for (int j = 1; j <= i; ++j) {
                if (num[j] > num[first])
                    first = j;
            }
            temp = num[first];
            num[first] = num[i];
            num[i] = temp;
        }
    }

    public static void main(String[] args) {
        double[] array = new double[args.length];
        for (int i = 0; i < args.length; ++i) {
            array[i] = Double.parseDouble(args[i]);
        }
        selectionSort(array);
        printArray(array);
    }

}

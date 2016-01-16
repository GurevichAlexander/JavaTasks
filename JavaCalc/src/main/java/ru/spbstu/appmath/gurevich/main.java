package ru.spbstu.appmath.gurevich;


public class main {
    public static void main(String[] args) {
        double var;
        if (args.length == 2)
            var = Double.parseDouble(args[1]);
        else if (args.length == 1)
            var = 0;
        else {
            System.out.println("Arguments error");
            return;
        }

        final Expression f;
        final Parser p = new Parser();
        try {
            f = p.parse(args[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            System.out.println(f.calc(var));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

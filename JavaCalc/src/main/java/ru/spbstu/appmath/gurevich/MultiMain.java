package ru.spbstu.appmath.gurevich;

import java.nio.file.Paths;

public class MultiMain {
    public static void main(String[] args) {
        if (args.length == 3) {
            final MultiCalc f = new MultiCalc();
            final String inFile = Paths.get("src", "main", "java", "ru", "spbstu", "appmath", "gurevich", args[0]).toString();
            final String outFile = Paths.get("src", "main", "java", "ru", "spbstu", "appmath", "gurevich", args[1]).toString();
            f.calcAndWrite(args[2], inFile, outFile);
        } else {
            System.out.println("Arguments error");
            return;
        }

    }
}
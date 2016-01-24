package ru.spbstu.appmath.gurevich;

import ru.spbstu.appmath.gurevich.exceptions.multicalcexceptions.RangeException;

import java.nio.file.Paths;

public class MultiMain {
    public static void main(String[] args) {
        if (args.length == 3) {
            final MultiCalc f = new MultiCalc();
            final String inFile = args[0];
            final String outFile = args[1];
            try{
                f.calcAndWrite(args[2], inFile, outFile);
            } catch(RangeException e){
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Arguments error");
        }
        return;
    }
}
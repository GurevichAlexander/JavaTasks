package ru.spbstu.appmath.gurevich;

import ru.spbstu.appmath.gurevich.exceptions.singlecalcexceptions.WrongSyntaxException;
import ru.spbstu.appmath.gurevich.exceptions.singlecalcexceptions.SingleCalcException;
import ru.spbstu.appmath.gurevich.exceptions.multicalcexceptions.RangeException;

import java.io.*;
import java.util.*;

public class MultiCalc {

    public void calcAndWrite(String sRange, String inFile, String outFile){
        try {
            final ParseRange range = new ParseRange(sRange);

            final File fileIn = new File(inFile);
            final File fileOut = new File(outFile);
            try {                                                           //Creating output file
                if (fileOut.createNewFile()) {
                    System.out.println("Created " + fileOut.getCanonicalPath());
                } else {
                    System.out.println("File already exists at " + fileOut.getCanonicalPath());
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return;
            }
            ArrayList<String> tasks = new ArrayList();
            try (Scanner scanner = new Scanner(fileIn)) {                   //Reading input file
                while (scanner.hasNextLine()) {
                    String task;
                    task = scanner.nextLine();
                    tasks.add(task);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Input file not found");
                return;
            }
            final List<ArrayList<String>> result = solveAll(range, tasks);
            writeFile(fileOut, result);
        } catch (RangeException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    /*
    * writes results into the file using appropriate format
    */
    protected static void writeFile(File file, List<ArrayList<String>> arr) {
        try (PrintWriter writer = new PrintWriter(file)) {
            int len[] = maxLengths(arr);
            for (int i = 0; i < arr.get(0).size(); i++) {
                for (int j = 0; j < arr.size(); j++) {
                    if (arr.get(j).size() != 1) {
                        int numSpaces = len[j] - arr.get(j).get(i).length();
                        for (int k = 0; k < numSpaces; k++)
                            writer.print(" ");
                        writer.print(arr.get(j).get(i));
                        if (j - 1 != arr.size())
                            writer.print("      ");
                    }
                }
                writer.println();
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }

    /*
     * returns vector of max lengths in every column
     */
    private static int[] maxLengths(List<ArrayList<String>> a) {
        int max[] = new int[a.size()];
        for (int j = 0; j < a.size(); j++) {
            max[j] = a.get(j).get(0).length();
            for (int i = 0; i < a.get(j).size(); i++)
                if (a.get(j).get(i).length() > max[j])
                    max[j] = a.get(j).get(i).length();
        }
        return max;
    }


    final List<ArrayList<String>> solveAll(ParseRange range, ArrayList<String> tasks) {
        final List<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        double from = range.getFrom();
        double to = range.getTo();
        double step = range.getStep();
        for (int j = 0; j < tasks.size(); j++) {
            String task = tasks.get(j);
            result.add(j, new ArrayList<String>());
            result.get(j).add(0, task);             //puts tasks into results file
            final Expression f;
            final Parser p = new Parser();
            try {
                f = p.parse(task);
                for (double i = from; i <= to; i += step) {
                    try {
                        result.get(j).add((int) ((i - from) / step + 1), Double.toString(f.calc(i)));
                    } catch (SingleCalcException e) {
                        result.get(j).add((int) ((i - from) / step + 1), e.getMessage());
                    }
                }
            } catch (WrongSyntaxException e) {
                System.out.println(e.getMessage() + ": " + task);
            }
        }
        return result;
    }

    /*
     * Parses string looking like "min:max" or "min:max:step"
     */
    public static class ParseRange {
        private double from;
        private double to;
        private double step;

        public ParseRange(String s) throws RangeException {
            s = s.trim();
            if (s.indexOf(':') == -1) {
                throw new RangeException("range format is incorrect");
            }
            String from = s.substring(0, s.indexOf(':'));
            try {
                this.from = Double.parseDouble(from);
            } catch (Exception e) {
                throw new RangeException("min value of range is incorrect");
            }
            s = s.substring(s.indexOf(':') + 1);
            if (s.indexOf(':') == -1) {
                try {
                    this.to = Double.parseDouble(s);
                } catch (Exception e) {
                    throw new RangeException("max value of range is incorrect");
                }
                this.step = 1;
            } else {
                try {
                    this.to = Double.parseDouble(s.substring(0, s.indexOf(':')));
                } catch (Exception e) {
                    throw new RangeException("max value of range is incorrect");
                }
                s = s.substring(s.indexOf(':') + 1);
                try {
                    this.step = Double.parseDouble(s);
                } catch (Exception e) {
                    throw new RangeException("step value of range is incorrect");
                }
                if (this.to < this.from)
                    throw new RangeException("range error: min>max!");
            }
        }

        public double getFrom() {
            return from;
        }

        public double getTo() {
            return to;
        }

        public double getStep() {
            return step;
        }
    }

}

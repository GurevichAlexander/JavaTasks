package ru.spbstu.appmath.gurevich;

import java.io.*;
import java.util.*;

public class MultiCalc {

    public void calcAndWrite(String sRange, String inFile, String outFile) {
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
        }
        ArrayList<String> tasks = new ArrayList();
        try {                                              //Reading input file
            Scanner scanner = new Scanner(fileIn);
            while (scanner.hasNextLine()) {
                StringBuilder task = new StringBuilder();
                task.append(scanner.nextLine());
                tasks.add(task.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found");
        }
        final List<ArrayList<Object>> result = solveAll(range, tasks);
        writeFile(fileOut, result);
    }

    /*
    * writes results into the file using appropriate format
    */
    protected static void writeFile(File file, List<ArrayList<Object>> arr) {
        try {
            PrintWriter writer = new PrintWriter(file);
            int len[] = maxLengths(arr);
            for (int i = 0; i < arr.get(0).size(); i++) {
                for (int j = 0; j < arr.size(); j++) {
                    if (arr.get(j).size() != 1) {
                        int numSpaces = len[j] - arr.get(j).get(i).toString().length();
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
    private static int[] maxLengths(List<ArrayList<Object>> a) {
        int max[] = new int[a.size()];
        for (int j = 0; j < a.size(); j++) {
            max[j] = a.get(j).get(0).toString().length();
            for (int i = 0; i < a.get(j).size(); i++)
                if (a.get(j).get(i).toString().length() > max[j])
                    max[j] = a.get(j).get(i).toString().length();
        }
        return max;
    }


    final List<ArrayList<Object>> solveAll(ParseRange range, ArrayList<String> tasks) {
        final List<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
        int from = range.getFrom();
        int to = range.getTo();
        int step = range.getStep();
        for (int j = 0; j < tasks.size(); j++) {
            String task = tasks.get(j);
            result.add(j, new ArrayList<Object>());
            result.get(j).add(0, task);             //puts tasks into results file
            final Expression f;
            final Parser p = new Parser();
            try {
                f = p.parse(task);
                for (int i = from; i <= to; i += step) {
                    try {
                        result.get(j).add(i - from + 1, f.calc(i));
                    } catch (Exception e) {
                        result.get(j).add(i - from + 1, e.getMessage());
                    }
                }
            } catch (Exception e) {
                for (int i = from; i <= to; i += step) {
                    result.get(j).add(i - from + 1, e.getMessage());
                }
            }
        }
        return result;
    }
    /*
     * Parses string looking like "min:max" or "min:max:step"
     */
    public static class ParseRange {
        private int from;
        private int to;
        private int step;

        public ParseRange(String s) {
            s = s.trim();
            String from = s.substring(0, s.indexOf(':'));
            this.from = Integer.parseInt(from);
            s = s.substring(s.indexOf(':') + 1);
            if (s.indexOf(':') == -1) {
                this.to = Integer.parseInt(s);
                s = "";
                this.step = 1;
            } else {
                this.to = Integer.parseInt(s.substring(0, s.indexOf(':')));
                s = s.substring(s.indexOf(':') + 1);
                this.step = Integer.parseInt(s);
            }
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public int getStep() {
            return step;
        }
    }

}

package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVFileComparer {
    private static final String DEFAULT_SEPARATOR = ",";
    private static final int DEFAULT_COLUMN_TO_COMPARE = 1;

    public static void main(String[] args) throws FileNotFoundException {

        String tmpA = "";
        String tmpB = "";
        Scanner scanner = null;
        String path = "./misc/";
        String ext = ".csv";
        String csvFileA = path + "a" + ext;
        String csvFileB = path + "b" + ext;
        List<String> linesA = new ArrayList<>();
        List<String> linesB = new ArrayList<>();
        List<List<String>> fileA = new ArrayList<>();
        List<List<String>> fileB = new ArrayList<>();
        List<List<String>> fileAnB = new ArrayList<>();

        try {
            scanner = new Scanner(new File(csvFileA));
            while (scanner.hasNext()) {
                linesA.add(scanner.nextLine());
            }
            System.out.println(linesA.get(0));

            scanner = new Scanner(new File(csvFileB));
            while (scanner.hasNext()) {
                linesB.add(scanner.nextLine());
            }
            System.out.println(linesB.get(0));

            for (String line : linesA) {
                fileA.add(parseLine(line));
            }

            for (String line : linesB) {
                fileB.add(parseLine(line));
            }

            scanner.close();

            tmpA = "";
            tmpB = "";
            for (int i = 0; i < fileA.size(); i++) {
                tmpA = fileA.get(i).get(DEFAULT_COLUMN_TO_COMPARE);
                for (int j = 0; j < fileB.size(); j++) {
                    tmpB = fileB.get(j).get(DEFAULT_COLUMN_TO_COMPARE);
                    if (tmpB.equals(tmpA)) {
                        System.out.println(tmpA);
                        fileAnB.add(fileA.get(i));
                        fileA.remove(i);
                        fileB.remove(j);
                    }
                }
            }

            System.out.println("A n B");
            for (int i = 0; i < fileAnB.size(); i++) {
                System.out.println(fileAnB.get(i));
            }
            writeFile(fileAnB, path, "AnB", ext);

            System.out.println("A \\ B");
            for (int i = 0; i < fileA.size(); i++) {
                System.out.println(fileA.get(i));
            }
            writeFile(fileA, path, "AmB", ext);

            System.out.println("B \\ A");
            for (int i = 0; i < fileB.size(); i++) {
                System.out.println(fileB.get(i));
            }
            writeFile(fileB, path, "BmA", ext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR);
    }

    public static List<String> parseLine(String cvsLine, String separator) {
        Scanner sc = null;
        List<String> result = new ArrayList<>();

        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if ((separator == "") || (separator == " ")) {
            separator = DEFAULT_SEPARATOR;
        }

        sc = new Scanner(cvsLine).useDelimiter(separator);
        while (sc.hasNext()) {
            result.add(sc.next());
        }
        sc.close();

        return result;
    }

    public static void writeFile(List<List<String>> file, String fpath, String fname, String fext) throws IOException {
        FileWriter fw = new FileWriter(fpath + fname + fext);

        for (int i = 0; i < file.size(); i++) {
            fw.write(file.get(i).toString() + "\n");
        }

        fw.close();
    }
}
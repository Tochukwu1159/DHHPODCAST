package com.netshiftdigital.dhhpodcast.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ErrorDemo {
        public static void main(String[] args) {
            try {
                // Error 1: NullPointerException
                String str = null;
                System.out.println(str.length());

            } catch (NullPointerException ex) {
                System.out.println("Error 1: NullPointerException - Cannot access length of null string");
            }

            try {
                // Error 2: ArrayIndexOutOfBoundsException
                int[] array = new int[5];
                System.out.println(array[10]);

            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Error 2: ArrayIndexOutOfBoundsException - Array index out of bounds");
            }

            try {
                // Error 3: NumberFormatException
                String numStr = "abc";
                int num = Integer.parseInt(numStr);

            } catch (NumberFormatException ex) {
                System.out.println("Error 3: NumberFormatException - Cannot parse non-numeric string to integer");
            }

            try {
                // Error 4: FileNotFoundException
                FileReader file = new FileReader("non_existent_file.txt");

            } catch (FileNotFoundException ex) {
                System.out.println("Error 4: FileNotFoundException - File not found");
            }

            try {
                // Error 5: IOException
                FileWriter writer = new FileWriter("example.txt");
                writer.write("Hello");

            } catch (IOException ex) {
                System.out.println("Error 5: IOException - Error writing to file");
            }

            try {
                // Error 6: ArithmeticException
                int x = 5 / 0;

            } catch (ArithmeticException ex) {
                System.out.println("Error 6: ArithmeticException - Division by zero");
            }

            try {
                // Error 7: ClassCastException
                Object obj = "Hello";
                Integer intObj = (Integer) obj;

            } catch (ClassCastException ex) {
                System.out.println("Error 7: ClassCastException - Cannot cast string to integer");
            }

            try {
                // Error 8: IllegalArgumentException
                List<String> list = new ArrayList<>();
                list.add(null);

            } catch (IllegalArgumentException ex) {
                System.out.println("Error 8: IllegalArgumentException - Cannot add null element to list");
            }

            try {
                // Error 9: IllegalStateException
                List<String> list2 = new ArrayList<>();
                list2.add("Hello");
                list2.clear();
                System.out.println(list2.get(0));

            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Error 9: IndexOutOfBoundsException - List is empty");
            } catch (IllegalStateException ex) {
                System.out.println("Error 9: IllegalStateException - List is empty");
            }
        }
    }


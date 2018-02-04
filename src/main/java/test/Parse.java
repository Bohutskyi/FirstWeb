package test;

import java.util.Scanner;

public class Parse {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String buffer;
        while (!(buffer = scanner.nextLine()).equals("q")) {
            for (int i = 0, n = buffer.length(); i < n; ++i) {
                System.out.print("\\u" + Integer.toHexString((int) buffer.charAt(i)));
            }
            System.out.println();
        }
    }

}

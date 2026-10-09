package main;

import edu.princeton.cs.algs4.In;

import static utils.Utils.*;

public class FileReadDemo {
    public static void main(String[] args) {
        In in = new In(SHORT_WORDS_FILE);
        int i = 0;

        while (!in.isEmpty()) {
            i += 1;
            String nextLine = in.readLine(); //返回当前行并指向下一行
            System.out.print("Line " + i + " is: ");
            System.out.println(nextLine);
            System.out.print("After splitting on tab characters, the first word is: ");
            String[] splitLine = nextLine.split("\t");
            System.out.println(splitLine[0]);
        }
    }
}

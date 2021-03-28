package Experiiments;

import java.util.Arrays;

public class Memset {
    public void doIt(String[] args) {
        long startTime, runTime;
        int size = 100;

        if (args.length > 0)
            size = Integer.parseInt(args[0]);

        System.out.printf("Size: %d\n", size);

        boolean[][] data = new boolean[size][size];

        System.out.print("======================\nStarting double loop\n");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = false;
            }
        }
        runTime = System.currentTimeMillis() - startTime;
        System.out.print(runTime + " ms\n");

        System.out.print("======================\nStarting Array.fill()\n");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < data.length; i++) {
            Arrays.fill(data[i], false);
        }
        runTime = System.currentTimeMillis() - startTime;
        System.out.print(runTime + " ms\n");
    }
}
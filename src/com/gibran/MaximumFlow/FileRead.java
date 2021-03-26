package com.gibran.MaximumFlow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileRead {
    public Graph graphReader(String fileName) throws FileNotFoundException {
        try {
        Graph graph;
        File file = new File("Inputs/"  + fileName);
        FileInputStream fis = new FileInputStream(file);
        Scanner sc = new Scanner(fis);
        String nodeQty = sc.nextLine().trim();
        graph = new Graph(Integer.parseInt(nodeQty));

        int[] nodeConnections = new int[3];
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] count = line.split(" ");
            for(int j = 0; j < count.length; j++) {
                nodeConnections[j] = Integer.parseInt(count[j]);
            }
            graph.putEdge(nodeConnections[0], nodeConnections[1], nodeConnections[2]);
        }
        return graph;
        } catch(FileNotFoundException ex1){
            System.out.println("File "+ fileName +" does not exist!");
            return null;
        }
    }
}

package com.gibran.MaximumFlow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileRead {
    int numberOfNodes;
    int numberOfEdges;
    public Graph graphReader(String fileName) throws FileNotFoundException {
        try {
        Graph graph;
        //Get access to the file dataset
        File file = new File("Inputs/"  + fileName);
        FileInputStream fis = new FileInputStream(file);
        //Used to read the dataset values
        Scanner sc = new Scanner(fis);
        //Contains number of nodes in the first line
        String nodeQty = sc.nextLine().trim();
        //Initialised the Graph based on the number of nodes.
        graph = new Graph(Integer.parseInt(nodeQty));
        numberOfNodes = graph.getNumberOfNodes();

        //Storing Node 1, Node 2, Capacity in an error
        int[] nodeConnections = new int[3];
        //Goes line by line for each edge connection.
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] count = line.split(" ");//Removes spaces
            for(int j = 0; j < count.length; j++) {
                nodeConnections[j] = Integer.parseInt(count[j]);
            }
            //Adds an edge from each line
            graph.putEdge(nodeConnections[0], nodeConnections[1], nodeConnections[2]);
        }
        numberOfEdges = graph.getNumberOfEdges();
        return graph;

        } catch(FileNotFoundException ex1){
            System.out.println("File "+ fileName +" does not exist!");
            return null;
        }
    }
}

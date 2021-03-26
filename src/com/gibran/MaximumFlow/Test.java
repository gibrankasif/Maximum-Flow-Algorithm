package com.gibran.MaximumFlow;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
//        Graph graph = new Graph(5);
//        System.out.println(graph.getNumberOfNodes());
//        System.out.println(graph.getNumberOfEdges());
//        graph.putEdge(0,1, 4);
//        graph.putEdge(0,4,7);
//        System.out.println(graph.getNumberOfEdges());
//        System.out.println(graph.outDegree(0));
//        System.out.println(graph.inDegree(0));
//        graph.putEdge(1,0,2);
//        graph.removeEdge(0,1);
//        System.out.println(graph.degree(0));
//        System.out.println(graph.inDegree(0));
//        System.out.println(graph.getNumberOfEdges());

//        FileRead fileRead = new FileRead();
//        Graph graph1 = fileRead.graphReader("bridge_3.txt");
//        System.out.println(graph1.degree(0));
//        graph1.printGraph();
//        graph1.printEdges();
//        System.out.println(graph1.getNumberOfNodes());


//        System.out.println(graph1.getEdge(1, 17));

//        System.out.println(graph1.getNumberOfEdges());
//        System.out.println(graph1.inDegree(1));
//        System.out.println(graph1.outDegree(1));



        MaximumFlow maximumFlow = new MaximumFlow();
        FileRead fileRead = new FileRead();
        Graph graph1 = fileRead.graphReader("ladder_2.txt");
        graph1.printGraph();
        System.out.println();
        Graph graph21 = fileRead.graphReader("bridge_2.txt");
        graph21.printGraph();
//        System.out.println();
//        System.out.println(graph1.inDegree(2));
//
//        System.out.println(maximumFlow.findMaxFlow(graph1,0,5));
//
//        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
//        int parent[] = new int[graph1.getNumberOfNodes()];
//        boolean gh = breadthFirstSearch.bfs(graph1, 1, 5, parent);
//
//        System.out.println(gh);
//
//

    }
}
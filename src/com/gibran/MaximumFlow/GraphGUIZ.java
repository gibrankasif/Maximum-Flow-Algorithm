package com.gibran.MaximumFlow;

import org.jgrapht.Graph;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;

public class GraphGUIZ {



    public static void main(String[] args) {
        Graph<Integer,DefaultEdge> graph = new DefaultDirectedWeightedGraph<>(DefaultEdge.class);
        for (Integer i =0; i <= 6; i++) {
            graph.addVertex(i);
        }
        DefaultEdge defaultEdge = graph.addEdge(0,1);
        graph.setEdgeWeight(defaultEdge, 2);
        DefaultEdge defaultEdge2 = graph.addEdge(1,2);
        graph.setEdgeWeight(defaultEdge2, 3);  DefaultEdge defaultEdge3 = graph.addEdge(1,3);
        graph.setEdgeWeight(defaultEdge3, 4);  DefaultEdge defaultEdge4 = graph.addEdge(4,5);
        graph.setEdgeWeight(defaultEdge4, 7);  DefaultEdge defaultEdge5 = graph.addEdge(3,5);
        graph.setEdgeWeight(defaultEdge5, 2);  DefaultEdge defaultEdge6 = graph.addEdge(2,5);
        graph.setEdgeWeight(defaultEdge6, 6);





        EdmondsKarpMFImpl edmondsKarpMF;
        edmondsKarpMF = new EdmondsKarpMFImpl(graph);
        long start = System.nanoTime();
        System.out.println(        edmondsKarpMF.calculateMaximumFlow(0, 1));
        System.out.println(System.nanoTime()-start);

    }


}
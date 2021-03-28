package com.gibran.MaximumFlow;

public class MaximumFlow {
    BreadthFirstSearch breadthFirstSearch;
    public int findMaxFlow(Graph graph, int source, int sink) {

        if (source == sink) {
            return 0;
        }

        int numberOfNodes = graph.getNumberOfNodes();
        Graph residualGraph = new Graph(numberOfNodes);
        for(int i = 0; i < numberOfNodes; i++) {
            for(int j = 0; j < numberOfNodes; j++){
                residualGraph.getAdjacencyMatrix()[i][j] = graph.getAdjacencyMatrix()[i][j];
            }
        }
        int[] parent = new int[numberOfNodes];
        int maximumFlow = 0;
        int numAugmentedPaths = 0;

        while(breadthFirstSearch.bfs(residualGraph, source, sink, parent)) {
            //Bottle-Neck is 0;
            int bottleNeck = Integer.MAX_VALUE;
            for(int i = sink; i != source; i = parent[i]) {
                int j = parent[i];
                //finding the lowest capacity
                bottleNeck = Math.min(bottleNeck, residualGraph.getAdjacencyMatrix()[j][i]);

            }

            for (int i = sink; i != source; i = parent[i]) {
                int j = parent[i];
                residualGraph.getAdjacencyMatrix()[j][i] -= bottleNeck;
                residualGraph.getAdjacencyMatrix()[i][j] += bottleNeck;
            }

            maximumFlow += bottleNeck;
            numAugmentedPaths++;
//            System.out.println();
//            System.out.println("Number of paths: " + numAugmentedPaths);
//            System.out.println("The current flow values: " + bottleNeck);
//            System.out.println("Current max-flow total: " + maximumFlow);
        }

        return maximumFlow;
    }
}
